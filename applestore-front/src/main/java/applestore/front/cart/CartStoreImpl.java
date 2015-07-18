package applestore.front.cart;

import applestore.domain.cart.Cart;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * @author chanwook
 */
@Service
public class CartStoreImpl implements CartStore {
    @Override
    public Cart getCart(HttpSession session) {
        // 1. Search in HttpSession
        Cart cart = (Cart) session.getAttribute("_CART");
        if (cart == null) {
            final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            // 2. Search in Database
            final String userId = (String) authentication.getCredentials();
            cart = getCartInDatabase(userId);
            if (cart == null) {
                // 3. Create cart
                cart = createCart(userId);
            }
            session.setAttribute("_CART", cart);
        }
        return cart;
    }

    private Cart getCartInDatabase(String userId) {
        return null;
    }

    private Cart createCart(String userId) {
        return new Cart(userId);
    }
}
