package applestore.front.cart;

import applestore.domain.order.entity.OrderItem;

/**
 * @author chanwook
 */
public class OrderItemViewModel {
    private String productName;
    private OrderItem orderItem;

    public OrderItemViewModel() {
    }

    public OrderItemViewModel(OrderItem orderItem) {
        this.orderItem = orderItem;
        this.productName = orderItem.getOrderSku().getProduct().getProductName();
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public String getProductName() {
        return productName;
    }
}
