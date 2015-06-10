package applestore.api.framework;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * @author chanwook
 */
public class DateUtils {
    public static Date getDate(String dateTime) {
        final DateTimeFormatter fmt = org.joda.time.format.DateTimeFormat.forPattern("yyyy-MM-dd hh:mm:ss");
        return DateTime.parse(dateTime, fmt).toDate();
    }

    public static Date now() {
        return DateTime.now().toDate();
    }
}
