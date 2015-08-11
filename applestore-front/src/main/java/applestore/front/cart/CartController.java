package applestore.front.cart;

import applestore.domain.cart.entity.Cart;
import applestore.domain.order.entity.OrderItem;
import applestore.front.product.ProductForCartRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import r2.dustjs.spring.DustModel;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chanwook
 */
@Controller
public class CartController {

    @Autowired
    CartService cs;

    @Autowired
    CartStore cartStore;

    @RequestMapping(value = "/cart/addItem", method = RequestMethod.POST)
    public String addItem(ProductForCartRequest request, HttpSession session) {

        OrderItem orderItem = cs.createOrderItem(request);

        Cart cart = cartStore.getCart(session);
        cart.addItem(orderItem);
        cartStore.update(cart);

        return "redirect:/cart";
    }

    @RequestMapping(value = "/cart")
    public String viewCart(HttpSession session, DustModel model) {
        Cart cart = cartStore.getCart(session);

        List<OrderItem> itemList = cs.getOrderItem(cart.getItemList());
        List<OrderItemViewModel> viewModel = new ArrayList<OrderItemViewModel>();
        for (OrderItem oi : itemList) {
            OrderItemViewModel oivm = new OrderItemViewModel(oi);
            viewModel.add(oivm);
        }

        model.put("cart", cart);
        model.put("itemList", viewModel);
        return "cart";
    }
}
