package applestore.front.cart;

import applestore.domain.cart.entity.Cart;
import applestore.domain.order.OrderItem;
import applestore.front.product.ProductForCartRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chanwook
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartStore cartStore;

    @Override
    public void addItem(Cart cart, OrderItem orderItem) {
        cart.addItem(orderItem);
        cartStore.update(cart);
    }

    @Override
    public OrderItem createOrderItem(ProductForCartRequest request, Cart cart) {
        OrderItem item = new OrderItem();
        item.setProductId(request.getProductId());
        item.setSkuId(request.getSelectSkuId());
        item.setOrderCount(request.getSelectItemCount());
        return item;
    }
}
