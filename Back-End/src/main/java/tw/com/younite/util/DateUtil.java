package tw.com.younite.util;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateUtil {
    private Date parseDate;
    public Date parseDate (String birthday)  {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            parseDate = dateFormat.parse(birthday);
        } catch (ParseException e) {
            throw new IllegalArgumentException("無效的日期!");
        }
        return parseDate;
    }
}
