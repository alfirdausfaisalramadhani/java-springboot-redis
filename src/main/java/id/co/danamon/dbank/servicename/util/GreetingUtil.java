package id.co.danamon.dbank.servicename.util;

import id.co.danamon.dbank.servicename.constant.Gender;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

public class GreetingUtil {
    private GreetingUtil() {
    }

    public static String constructGreeting(Gender gender, String name) {
        String greeting = "Hello, ";

        int timeOfDay = LocalDateTime.now().getHour();
        if (timeOfDay >= 0 && timeOfDay < 12) {
            greeting = greeting.concat("Good Morning ");
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            greeting = greeting.concat("Good Afternoon ");
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            greeting = greeting.concat("Good Evening ");
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            greeting = greeting.concat("Good Night ");
        }

        if (StringUtils.isEmpty(name)) {
            name = "User";
        }

        if (Gender.MAN.equals(gender)) {
            return greeting.concat("Mr. ").concat(name);
        } else {
            return greeting.concat("Ms. ").concat(name);
        }

    }
}
