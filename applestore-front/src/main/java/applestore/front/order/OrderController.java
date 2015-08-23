package applestore.front.order;

import applestore.domain.cart.entity.Cart;
import applestore.domain.order.entity.Order;
import applestore.front.cart.CartStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * @author chanwook
 */
@Controller
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger("ORDER_LOG");

    @Autowired
    OrderService service;

    @Autowired
    CartStore cartStore;

    @RequestMapping(value = "/order/createOrder", method = RequestMethod.POST)
    public String createOrder(CreateOrderRequestForm requestForm, HttpSession session) {
        logger.info(">> 주문생성 >> " + requestForm);

        final Cart cart = cartStore.getCart(session);

        Order order = service.createOrder(cart, requestForm);

        // 주문 생성 by Activity : 주문번호 따고, OrderItem 상태 갱신하고(주문상태로), 카트 비운다음 갱신하고, ?? 확인하고,
        // 주문 시작 상태로 생성

        return "redirect:/order/orderForm";
    }

    @RequestMapping("/order/orderForm")
    public String orderForm() {

        return "orderForm";
    }
}
