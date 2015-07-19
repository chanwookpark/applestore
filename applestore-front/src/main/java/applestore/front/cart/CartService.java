package applestore.front.cart;

import applestore.domain.cart.entity.Cart;
import applestore.domain.order.OrderItem;
import applestore.front.product.ProductForCartRequest;

/**
 * @author chanwook
 */
public interface CartService {

    void addItem(Cart cart, OrderItem orderItem);

    OrderItem createOrderItem(ProductForCartRequest request, Cart cart);
}
