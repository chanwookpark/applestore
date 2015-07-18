package applestore.front.cart;

import applestore.domain.cart.Cart;
import applestore.domain.order.OrderItem;

/**
 * @author chanwook
 */
public interface CartService {
    Cart createCart(String credentials);

    void addItem(Cart cart, OrderItem orderItem);
}
