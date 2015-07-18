package applestore.front.cart;

import applestore.domain.cart.Cart;

import javax.servlet.http.HttpSession;

/**
 * @author chanwook
 */
public interface CartStore {
    Cart getCart(HttpSession session);
}
