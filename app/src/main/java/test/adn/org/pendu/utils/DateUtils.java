package test.adn.org.pendu.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cagecfi on 14/07/2017.
 */

public class DateUtils {
    public static SimpleDateFormat sdf = new SimpleDateFormat();

    public static String dateHistorique(Date date, String format) {
        if (format == null)
            format = "dd/MM/yyyy";
        sdf.applyPattern(format);
        return sdf.format(date);
    }
}
