package applestore.front.order.paypal;

import org.springframework.util.StringUtils;

import java.util.HashMap;

/**
 * @author chanwook
 */
public class SetExpressCheckoutResponse extends HashMap<String, String> {

    public static SetExpressCheckoutResponse create(String text) {
        SetExpressCheckoutResponse res = new SetExpressCheckoutResponse();
        final String[] arr = StringUtils.split(text, "&");
        for (String e : arr) {
            final String[] keyValue = e.split("=");
            res.put(keyValue[0], keyValue[1]);
        }
        return res;
    }

    public String getToken() {
        return get("TOKEN");
    }

    public String getTimeStamp() {
        return get("TIMESTAMP");
    }

    public String getCorrelationId() {
        return get("CORRELATIONID");
    }

    public String getAck() {
        return get("ACK");
    }

    public String getVersion() {
        return get("VERSION");
    }

    public String getBuild() {
        return get("BUILD");
    }

}
