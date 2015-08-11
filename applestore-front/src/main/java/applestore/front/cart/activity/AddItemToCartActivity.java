package applestore.front.cart.activity;

import applestore.domain.order.entity.OrderItem;
import applestore.domain.order.entity.OrderItemStatus;
import applestore.domain.order.repository.OrderItemJpaRepository;
import applestore.domain.product.entity.Product;
import applestore.domain.product.entity.Sku;
import applestore.domain.product.repository.ProductJpaRepository;
import applestore.framework.workflow.Activity;
import applestore.framework.workflow.RequestMap;
import applestore.framework.workflow.ResponseMap;
import applestore.framework.workflow.WorkflowException;
import applestore.front.price.PricingService;
import applestore.front.product.ProductForCartRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chanwook
 */
@Component
public class AddItemToCartActivity implements Activity {

    @Autowired
    ProductJpaRepository pr;

    @Autowired
    OrderItemJpaRepository oir;

    @Autowired
    PricingService ps;

    @Override
    public String getProcessId() {
        return "addItemToCart";
    }

    @Transactional
    @Override
    public void execute(RequestMap requestMap, ResponseMap responseMap) {
        // 화면에서 보낸 product/sku 버전과 맞는 요청인지 확인하고 나서
        // product/sku 정보를 사용해 OrderItem 생성
        // 결국 OI는 가격계산, 주문수량이 정보 필요. 가격은 별도 서비스로 추출
        // 재고 체크 등의 validation 통과 필요
        // blc는 이 orderItem을 카트때부터 Order와 관련을 맺어 버리던데..
        // workflow는 직접 심플하게 만들자..

        final ProductForCartRequest cartRequest = (ProductForCartRequest) requestMap.get("cartRequest");

        // 1. Product/SKU를 조회해 요청한 주문 정보가 가능한지 확인
        final Product product = pr.findOne(cartRequest.getProductId());
        validateCartRequest(cartRequest, product);

        // 2. OI를 생성한다
        OrderItem orderItem = createOrderItem(cartRequest, product);

        // 3. 가격을 계산한다
        long orderPrice = ps.getPrice(product, orderItem);
        orderItem.setOrderPrice(orderPrice);

        // 4. OI를 저장 처리한다
        saveOrderItem(orderItem);

        responseMap.put("createOrderItem", orderItem);
    }

    private void saveOrderItem(OrderItem orderItem) {
        oir.save(orderItem);
    }

    private void validateCartRequest(ProductForCartRequest cartRequest, Product product) {
        if (product == null || !product.getProductId().equals(cartRequest.getProductId())) {
            throw new WorkflowException("상품ID(" + cartRequest.getProductId() + ")에 해당하는 상품이 존재하지 않습니다!");
        }

        if (!product.hasSku(cartRequest.getSelectSkuId())) {
            throw new WorkflowException("상품ID(" + cartRequest.getProductId() + "의 SKU ID(" + cartRequest.getSelectSkuId() + ")에 해당하는 SKU가 존재하지 않습니다!");
        }

        if (product.getSku(cartRequest.getSelectSkuId()).getSalesStock() < cartRequest.getOrderQuantity()) {
            throw new WorkflowException("판매를 위한 재고가 충분하지 못합니다! (상품ID:" + cartRequest.getProductId() + ", SKUID: " + cartRequest.getSelectSkuId() + ")");
        }
    }

    private OrderItem createOrderItem(ProductForCartRequest request, Product product) {
        final Sku orderSku = product.getSku(request.getSelectSkuId());
        OrderItem item = new OrderItem(orderSku, OrderItemStatus.IN_CART);
        item.setOrderQuantity(request.getOrderQuantity());
        return item;
    }
}
