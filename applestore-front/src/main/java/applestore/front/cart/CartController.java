package applestore.front.cart;

import applestore.domain.cart.Cart;
import applestore.domain.order.OrderItem;
import applestore.front.product.ProductFormForCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import r2.dustjs.spring.DustModel;

import javax.servlet.http.HttpSession;

/**
 * @author chanwook
 */
@Controller
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    CartStore cartStore;

    @RequestMapping(value = "/cart/addItem", method = RequestMethod.POST)
    public String addItem(ProductFormForCart form, HttpSession session) {
        Cart cart = cartStore.getCart(session);

        OrderItem orderItem = createOrderItem(form);
        cartService.addItem(cart, orderItem);

        return "redirect:/cart";
    }

    @RequestMapping(value = "/cart")
    public String viewCart(HttpSession session, DustModel model) {
        Cart cart = cartStore.getCart(session);

        model.put("cart", cart);
        return "cart";
    }

    private OrderItem createOrderItem(ProductFormForCart form) {
        OrderItem item = new OrderItem();
        item.setProductId(form.getProductId());
        item.setSkuId(form.getSelectSkuId());
        item.setOrderCount(form.getSelectItemCount());
        return item;
    }
}
