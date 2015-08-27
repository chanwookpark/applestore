package applestore.front.order;

import applestore.domain.cart.entity.Cart;
import applestore.domain.order.entity.Order;

/**
 * @author chanwook
 */
public interface OrderService {
    Order createOrder(Cart cart, CreateOrderRequestForm requestForm);

    void confirmOrder(Order order);
}
