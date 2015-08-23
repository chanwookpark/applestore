package applestore.front.order.paypal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * @author chanwook
 */
@Controller
public class PaypalCheckoutController {

    public static final String PAYPAL_NVP_SANDBOX = "https://api-3t.sandbox.paypal.com/nvp";
    public static final String VERSION = "124.0";
    public static final String USER = "sdk-three_api1.sdk.com";
    public static final String PASSWORD = "QFZCWN5HZM8VBG7Q";
    public static final String SIGNATURE = "A-IzJhZZjhg29XQ2qnhapuwxIDzyAZQ92FRP5dqBzVesOkzbdUONzmOU";

    private final Logger logger = LoggerFactory.getLogger(PaypalCheckoutController.class);

    final RestTemplate template = new RestTemplate();

    @RequestMapping(value = "/checkout/paypal/start")
    public String checkoutStart(HttpSession session) {

        // TODO 주문 절차를 동일하게 따라야 함.. 일단 AMOUNT를 알아야 함..

        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(PAYPAL_NVP_SANDBOX);
        uri.queryParam("METHOD", "SetExpressCheckout");
        uri.queryParam("VERSION", VERSION);
        addAuthentication(uri);
        mockPayment(uri);
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

    private void mockPayment(UriComponentsBuilder uri) {
        uri.queryParam("PAYMENTREQUEST_0_AMT", "1,234,000.00"); // if known, set value
        uri.queryParam("PAYMENTREQUEST_0_CURRENCYCODE", "USD"); //default is 'USD'
        uri.queryParam("PAYMENTREQUEST_0_PAYMENTACTION", "SALE"); // default is 'SALE'
    }

    private void addAuthentication(UriComponentsBuilder uri) {
        uri.queryParam("USER", USER); //if sandbox, always use this;
        uri.queryParam("PWD", PASSWORD); //if sandbox, always use this;
        uri.queryParam("SIGNATURE", SIGNATURE); //if sandbox, always use this;
    }

    @RequestMapping(value = "/checkout/paypal/process")
    public String process(String token, @RequestParam("PayerID") String payerId, DustModel model) {

        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(PAYPAL_NVP_SANDBOX);
        uri.queryParam("METHOD", "GetExpressCheckoutDetails");
        uri.queryParam("VERSION", VERSION);
        addAuthentication(uri);
        uri.queryParam("TOKEN", token);

        final ResponseEntity<String> response = template.getForEntity(uri.toUriString(), String.class);
        logger.info("Paypal 'GetExpressCheckoutDetails' call response: " + response);

        final GetExpressCheckoutDetailsResponse result = PaypalNvpResponseMapper.convert(response.getBody(), GetExpressCheckoutDetailsResponse.class);
        logger.info("Paypal 'GetExpressCheckoutDetails' response convert: " + result);

        // TODO 주문 완료 정보를 받아서 주문을 생성하고 여기서 주문 생성 등의 SHOP 내부 프로세스를 실행해 처리

        model.put("token", token);
        model.put("payerId", payerId);
        return "paypalOrderForm";
    }

    @RequestMapping(value = "/checkout/paypal/confirm", method = RequestMethod.POST)
    @ResponseBody
    public String confirm(String token, String payerId) {
        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(PAYPAL_NVP_SANDBOX);
        uri.queryParam("METHOD", "DoExpressCheckoutPayment");
        uri.queryParam("VERSION", VERSION);
        uri.queryParam("TOKEN", token);
        uri.queryParam("PAYERID", payerId);
        addAuthentication(uri);
        mockPayment(uri);

        final ResponseEntity<String> response = template.getForEntity(uri.toUriString(), String.class);
        logger.info("Paypal 'DoExpressCheckoutPayment' call response: " + response);

        final DoExpressCheckoutPaymentResponse result = PaypalNvpResponseMapper.convert(response.getBody(), DoExpressCheckoutPaymentResponse.class);
        logger.info("Paypal 'DoExpressCheckoutPayment' response convert: " + result);

        return "OK!?";
    }

    @RequestMapping(value = "/checkout/paypal/cancel")
    @ResponseBody
    public String cancel() {
        return "cancel!?";
    }
}
