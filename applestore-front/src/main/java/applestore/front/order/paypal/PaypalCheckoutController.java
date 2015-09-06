package applestore.front.order.paypal;

import applestore.domain.cart.document.Cart;
import applestore.domain.order.entity.Order;
import applestore.domain.order.entity.OrderItem;
import applestore.front.cart.OrderItemDTO;
import applestore.front.cart.service.CartService;
import applestore.front.cart.service.CartStore;
import applestore.front.order.CreateOrderRequestForm;
import applestore.front.order.OrderService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import r2.dustjs.spring.DustModel;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author chanwook
 */
@Controller
public class PaypalCheckoutController {

    final ModelMapper m = new ModelMapper();

    public static final String PAYPAL_NVP_SANDBOX = "https://api-3t.sandbox.paypal.com/nvp";
    public static final String VERSION = "124.0";
    public static final String USER = "sdk-three_api1.sdk.com";
    public static final String PASSWORD = "QFZCWN5HZM8VBG7Q";
    public static final String SIGNATURE = "A-IzJhZZjhg29XQ2qnhapuwxIDzyAZQ92FRP5dqBzVesOkzbdUONzmOU";

    private final Logger logger = LoggerFactory.getLogger(PaypalCheckoutController.class);

    final RestTemplate template = new RestTemplate();

    @Autowired
    CartService cs;

    @Autowired
    CartStore cartStore;

    @Autowired
    OrderService service;

    @RequestMapping(value = "/checkout/paypal/start")
    public String checkoutStart(@RequestParam(value = "toa", required = true) long reqToa) {

        // TODO 주문 절차를 동일하게 따라야 함.. 일단 AMOUNT를 알아야 함..

        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(PAYPAL_NVP_SANDBOX);
        uri.queryParam("METHOD", "SetExpressCheckout");
        uri.queryParam("VERSION", VERSION);
        addAuthentication(uri);
        mockPayment(uri, reqToa);
        uri.queryParam("RETURNURL", "http://localhost:10001/checkout/paypal/process");
        uri.queryParam("CANCELURL", "http://localhost:10001/checkout/paypal/cancel");

        final ResponseEntity<String> response =
                template.getForEntity(uri.toUriString(), String.class);

        logger.info("Paypal 'SetExpressCheckout' call response: " + response);

        SetExpressCheckoutResponse result =
                PaypalNvpResponseMapper.convert(response.getBody(), SetExpressCheckoutResponse.class);

        String redirectUrl = "https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=";
        return "redirect:" + redirectUrl + result.getToken();
    }

    @RequestMapping(value = "/checkout/paypal/process")
    public String process(String token,
                          @RequestParam("PayerID") String payerId, HttpSession session, DustModel model) {

        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(PAYPAL_NVP_SANDBOX);
        uri.queryParam("METHOD", "GetExpressCheckoutDetails");
        uri.queryParam("VERSION", VERSION);
        addAuthentication(uri);
        uri.queryParam("TOKEN", token);

        final ResponseEntity<String> response = template.getForEntity(uri.toUriString(), String.class);
        logger.info("Paypal 'GetExpressCheckoutDetails' call response: " + response);

        final GetExpressCheckoutDetailsResponse paypalOrder = PaypalNvpResponseMapper.convert(response.getBody(), GetExpressCheckoutDetailsResponse.class);
        logger.info("Paypal 'GetExpressCheckoutDetails' response convert: " + paypalOrder);

        // TODO 주문서 보여주는 컨트롤러와 동일하게 처리. 무슨 일을 해야할까...
        // paypal에서 주문가격, 결재 상세, 배송지 등에 대한 정보를 받아와 화면에 볼 수 있도록 처리..

        Cart cart = cartStore.getCart(session);

        List<OrderItem> itemList = cs.getOrderItem(cart.getItemList());

        model.put("token", token);
        model.put("payerId", payerId);
        model.put("itemList", m.map(itemList, new TypeToken<List<OrderItemDTO>>(){}.getType()));
        model.put("orderInfo", paypalOrder);

        return "paypalOrderForm";
    }

    @RequestMapping(value = "/checkout/paypal/confirm", method = RequestMethod.POST)
    public String confirm(String token, String payerId,
                          CreateOrderRequestForm reqForm, HttpSession session) {
        final Cart cart = cartStore.getCart(session);
        Order order = service.createOrder(cart, reqForm);

        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(PAYPAL_NVP_SANDBOX);
        uri.queryParam("METHOD", "DoExpressCheckoutPayment");
        uri.queryParam("VERSION", VERSION);
        uri.queryParam("TOKEN", token);
        uri.queryParam("PAYERID", payerId);
        addAuthentication(uri);
        mockPayment(uri, 0L);

        final ResponseEntity<String> response = template.getForEntity(uri.toUriString(), String.class);
        logger.info("Paypal 'DoExpressCheckoutPayment' call response: " + response);

        final DoExpressCheckoutPaymentResponse result = PaypalNvpResponseMapper.convert(response.getBody(), DoExpressCheckoutPaymentResponse.class);
        logger.info("Paypal 'DoExpressCheckoutPayment' response convert: " + result);

        service.confirmOrder(order);

        return "redirect:/order/orderSuccess?orderId=" + order.getOrderId();
    }

    @RequestMapping(value = "/checkout/paypal/cancel")
    @ResponseBody
    public String cancel() {
        return "cancel!?";
    }


    private void mockPayment(UriComponentsBuilder uri, long reqToa) {
        uri.queryParam("PAYMENTREQUEST_0_AMT", reqToa); // if known, set value
        uri.queryParam("PAYMENTREQUEST_0_CURRENCYCODE", "USD"); //default is 'USD'
        uri.queryParam("PAYMENTREQUEST_0_PAYMENTACTION", "SALE"); // default is 'SALE'
    }

    private void addAuthentication(UriComponentsBuilder uri) {
        uri.queryParam("USER", USER); //if sandbox, always use this;
        uri.queryParam("PWD", PASSWORD); //if sandbox, always use this;
        uri.queryParam("SIGNATURE", SIGNATURE); //if sandbox, always use this;
    }
}
