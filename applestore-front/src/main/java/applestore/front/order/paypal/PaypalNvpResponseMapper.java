package applestore.front.order.paypal;

import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author chanwook
 */
public class PaypalNvpResponseMapper {
    public static <T> T convert(String text, Class<? extends Map> target) {
        try {
            Map response = target.newInstance();
            final String[] arr = StringUtils.split(text, "&");
            for (String e : arr) {
                final String[] keyValue = e.split("=");
                response.put(keyValue[0], keyValue[1]);
            }
            return (T) response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
