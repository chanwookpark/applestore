package applestore.front.cart;

import applestore.domain.cart.entity.Cart;

import javax.servlet.http.HttpSession;

/**
 * @author chanwook
 */
public interface CartStore {
    Cart getCart(HttpSession session);

    void update(Cart cart);
}
