package toyproject.simulated_stock.domain.stock.overall.config;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Getter
@Component
public class DateConfig {
    public String getFiveDaysFromToday(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        calendar.add(Calendar.DATE, -5);
        String fiveDaysFromToday = format.format(calendar.getTime());
        return fiveDaysFromToday;
    }
}
