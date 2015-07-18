package applestore.front.cart;

import applestore.domain.cart.Cart;
import applestore.domain.order.OrderItem;
import org.springframework.stereotype.Service;

/**
 * @author chanwook
 */
@Service
public class CartServiceImpl implements CartService {
    @Override
    public Cart createCart(String credentials) {
        //DB에서 조회하고

        //그래도 없으면 생성
        return new Cart();
    }

    @Override
    public void addItem(Cart cart, OrderItem orderItem) {
        cart.addItem(orderItem);
    }
}
