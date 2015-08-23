package applestore.front.order.paypal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpSession;

/**
 * @author chanwook
 */
@Controller
public class PaypalCheckoutController {

    private final Logger logger = LoggerFactory.getLogger(PaypalCheckoutController.class);

    final RestTemplate template = new RestTemplate();

    @RequestMapping(value = "/checkout/paypal/start")
    public String checkoutStart(HttpSession session) {

        // TODO 주문 절차를 동일하게 따라야 함.. 일단 AMOUNT를 알아야 함..

        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString("https://api-3t.sandbox.paypal.com/nvp");
        uri.queryParam("METHOD", "SetExpressCheckout");
        uri.queryParam("VERSION", "124.0");
        uri.queryParam("USER", "sdk-three_api1.sdk.com"); //if sandbox, always use this;
        uri.queryParam("PWD", "QFZCWN5HZM8VBG7Q"); //if sandbox, always use this;
        uri.queryParam("SIGNATURE", "A-IzJhZZjhg29XQ2qnhapuwxIDzyAZQ92FRP5dqBzVesOkzbdUONzmOU"); //if sandbox, always use this;
        uri.queryParam("PAYMENTREQUEST_0_AMT", "1,234,000.00"); // if known, set value
        uri.queryParam("PAYMENTREQUEST_0_CURRENCYCODE", "USD"); //default is 'USD'
        uri.queryParam("RETURNURL", "http://localhost:10001/checkout/paypal/ok");
        uri.queryParam("CANCELURL", "http://localhost:10001/checkout/paypal/cancel");
        uri.queryParam("PAYMENTREQUEST_0_PAYMENTACTION", "SALE"); // default is 'SALE'

        final ResponseEntity<String> response =
                template.getForEntity(uri.toUriString(), String.class);

        logger.info("Paypal 'SetExpressCheckout' call response: " + response);

        SetExpressCheckoutResponse res = SetExpressCheckoutResponse.create(response.getBody());

        String redirectUrl = "https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=";
        return "redirect:" + redirectUrl + res.getToken();
    }

    @RequestMapping(value = "/checkout/paypal/ok")
    @ResponseBody
    public String ok() {
        return "ok!?";
    }

    @RequestMapping(value = "/checkout/paypal/cancel")
    @ResponseBody
    public String cancel() {
        return "cancel!?";
    }
}
