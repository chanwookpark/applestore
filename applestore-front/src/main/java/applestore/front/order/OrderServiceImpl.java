package applestore.front.order;

import applestore.domain.cart.entity.Cart;
import applestore.domain.order.entity.Order;
import applestore.domain.order.entity.OrderItem;
import applestore.domain.order.entity.OrderItemStatus;
import applestore.domain.order.repository.OrderItemJpaRepository;
import applestore.domain.order.repository.OrderJpaRepository;
import applestore.front.product.service.ProductService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author chanwook
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    ProductService productService;

    @Autowired
    OrderItemJpaRepository oir;

    @Autowired
    OrderJpaRepository or;

    @Transactional
    @Override
    public Order createOrder(Cart cart, CreateOrderRequestForm requestForm) {
        // cart의 정보와 요청 정보가 맞는지 확인

        // 1. RequestForm의 수량이 있는지 확인
        final List<Long> orderItemIds = requestForm.getOrderItemIds();
        final List<OrderItem> orderItemList = oir.findByOrderItemIdIn(orderItemIds);

        List<Long> skuIdList = new ArrayList<Long>();
        for (OrderItem oi : orderItemList) {
            skuIdList.add(oi.getOrderSku().getSkuId());
        }

        try {
            productService.checkProductQuantity(skuIdList, requestForm.getOrderQuantities());
        } catch (OrderException oe) {
            throw new OrderException("주문이 불가능한 상품입니다! 주문 수량이 부족합니다! ");
        }

        // 2. 주문 정보가 장바구니와 맞는지 확인. 틀리면 갱신하기
        for (int index = 0; index < orderItemList.size(); index++) {
            final OrderItem orderItem = orderItemList.get(index);
            final Integer quantityInForm = requestForm.getOrderQuantities().get(index);
            if (orderItem.getOrderQuantity() != quantityInForm) {
                orderItem.setOrderQuantity(quantityInForm);
            }
        }

        // 3. 주문 정보 생성
        Order order = saveOrder(orderItemList);

        // 4. 주문 아이템 상태&정보 갱신
        for (int index = 0; index < orderItemList.size(); index++) {
            final OrderItem orderItem = orderItemList.get(index);
            orderItem.setStatus(OrderItemStatus.IN_ORDER);
        }
        oir.save(orderItemList);

        return order;
    }

    @Transactional
    @Override
    public void confirmOrder(Order order) {
        for (OrderItem oi : order.getOrderItemList()) {
            oi.setStatus(OrderItemStatus.COMPLETE);
        }

        oir.save(order.getOrderItemList());
    }

    private Order saveOrder(List<OrderItem> orderItemList) {
        Order order = new Order();
        order.setOrderItemList(orderItemList);
        for (OrderItem oi : orderItemList) {
            oi.setOrder(order);
        }

        final Date now = DateTime.now().toDate();
        order.setUpdated(now);
        order.setCreated(now);

        or.save(order);

        return order;
    }

}
