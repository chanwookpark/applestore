package applestore.front.cart;

import applestore.domain.cart.entity.Cart;
import applestore.domain.order.OrderItem;
import applestore.front.product.ProductForCartRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chanwook
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartStore cartStore;

    @Override
    public void addItem(Cart cart, OrderItem orderItem) {
        cart.addItem(orderItem);
        cartStore.update(cart);
    }

    @Transactional
    @Override
    public OrderItem createOrderItem(ProductForCartRequest request, Cart cart) {
        OrderItem orderItem = getOrderItem(request);
        // 화면에서 보낸 product/sku 버전과 맞는 요청인지 확인하고 나서
        // product/sku 정보를 사용해 OrderItem 생성
        // 결국 OI는 가격계산, 주문수량이 정보 필요. 가격은 별도 서비스로 추출
        // 재고 체크 등의 validation 통과 필요
        // blc는 이 orderItem을 카트때부터 Order와 관련을 맺어 버리던데..
        // workflow는 직접 심플하게 만들자..
        addItem(cart, orderItem);
        return orderItem;
    }

    private OrderItem getOrderItem(ProductForCartRequest request) {
        OrderItem item = new OrderItem();
        item.setProductId(request.getProductId());
        item.setSkuId(request.getSelectSkuId());
        item.setOrderQuantity(request.getSelectItemQuantity());
        return item;
    }
}
