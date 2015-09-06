package applestore.front.cart.service;

import applestore.domain.cart.document.Cart;

import javax.servlet.http.HttpSession;

/**
 * @author chanwook
 */
public interface CartStore {
    Cart getCart(HttpSession session);

    void update(Cart cart);
}
