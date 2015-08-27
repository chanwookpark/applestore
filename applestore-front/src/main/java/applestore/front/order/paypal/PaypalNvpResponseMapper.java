package applestore.front.order.paypal;

import org.springframework.util.StringUtils;

import java.net.URLDecoder;
import java.util.Map;

/**
 * @author chanwook
 */
public class PaypalNvpResponseMapper {
    public static <T> T convert(String text, Class<? extends Map> target) {
        try {
            Map response = target.newInstance();
            final String[] arr = StringUtils.tokenizeToStringArray(text, "&");
            for (String e : arr) {
                final String[] keyValue = e.split("=");
                response.put(keyValue[0], URLDecoder.decode(keyValue[1], "UTF-8"));
            }
            return (T) response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
