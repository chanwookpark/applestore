package applestore.front.cart;

import applestore.domain.cart.entity.Cart;
import applestore.domain.order.entity.OrderItem;
import applestore.front.cart.service.CartService;
import applestore.front.cart.service.CartStore;
import applestore.front.price.PriceService;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import r2.dustjs.spring.DustModel;

import javax.servlet.http.HttpSession;
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

    @Autowired
    PriceService ps;

    @RequestMapping(value = "/cart/addItem", method = RequestMethod.POST)
    public String addItem(ProductOrderRequest request, HttpSession session) {

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
//        List<OrderItemDTO> viewModel = new ArrayList<OrderItemDTO>();
//        for (OrderItem oi : itemList) {
//            OrderItemDTO d = new OrderItemDTO(oi);
//            viewModel.add(d);
//        }

        model.bind("itemList", itemList, new TypeToken<List<OrderItemDTO>>(){});
        model.put("cart", cart);
        model.put("totalOrderAmount", ps.getTotalOrderAmount(itemList));
        return "cart";
    }
}
