package applestore.front.order.paypal;

import java.util.HashMap;

/**
 * @author chanwook
 */
public class SetExpressCheckoutResponse extends HashMap<String, String> {

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
