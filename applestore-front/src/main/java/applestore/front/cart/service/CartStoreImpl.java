package applestore.front.cart.service;

import applestore.domain.cart.document.Cart;
import applestore.domain.cart.repository.CartMongoRepository;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;

import static applestore.domain.cart.document.CartStatus.EXPIRED;
import static applestore.domain.cart.document.CartStatus.OPEN;

/**
 * @author chanwook
 */
@Service
public class CartStoreImpl implements CartStore {

    // 7 * 24 * 60 * 60 (1주일을 초로)
    int expireTime = 604800;

    @Autowired
    CartMongoRepository cr;

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

    @Override
    public void update(Cart cart) {
        cart.setUpdated(DateTime.now().toDate());
        cr.save(cart);
    }

    private Cart getCartInDatabase(String userId) {
        Cart openCart = cr.findOpenCart(userId);
        // 유효 기간이 지난 카트인지 확인
        if (openCart != null && hasExpired(openCart.getUpdated())) {
            openCart.setStatus(EXPIRED);
            cr.save(openCart); //status 갱신
            openCart = null;
        }
        return openCart;
    }

    private boolean hasExpired(Date updatedDate) {
        long updated = updatedDate.getTime();
        long current = DateTimeUtils.currentTimeMillis();
        final Period period = new Period(updated, current);
        final int gapTime = period.getMinutes();
        return gapTime > expireTime ? true : false;
    }

    private Cart createCart(String userId) {
        final Cart cart = new Cart(userId, OPEN, DateTime.now().toDate());
        return cr.save(cart);
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }
}
