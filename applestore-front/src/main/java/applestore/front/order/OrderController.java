package applestore.front.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author chanwook
 */
@Controller
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger("ORDER_LOG");

    @RequestMapping(value = "/order/createOrder", method = RequestMethod.POST)
    public String createOrder(CreateOrderRequestForm requestForm) {

        logger.info(">> 주문생성 >> " + requestForm);

        // 수량 정보가 변경되었는지 확인하고 갱신 (재고가 있는지 확인) to Activity

        // 주문 생성 by Activity : 주문번호 따고, OrderItem 상태 갱신하고(주문상태로), 카트 비운다음 갱신하고, ?? 확인하고,
        // 주문 시작 상태로 생성

        return "redirect:/order/orderForm";
    }

    @RequestMapping("/order/orderForm")
    public String orderForm() {

        return "orderForm";
    }
}
