package util;

import id.co.ist.mobile.servicename.constant.Gender;
import id.co.ist.mobile.servicename.util.GreetingUtil;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static junit.framework.TestCase.assertTrue;

public class GreetingUtilTest {


    @Test
    public void constructGreetingTestMorningMen() {
        LocalDateTime expected = LocalDateTime.of(2021, 11, 1, 2, 1);
        try (MockedStatic<LocalDateTime> theMock = Mockito.mockStatic(LocalDateTime.class)) {
            theMock.when(LocalDateTime::now)
                    .thenReturn(expected);
            String greeting = GreetingUtil.constructGreeting(Gender.MAN, "pria");

            assertTrue(greeting.contains("Mr."));
            assertTrue(greeting.contains("Morning"));
        }
    }

    @Test
    public void constructGreetingTestAfternoonWomen() {
        LocalDateTime expected = LocalDateTime.of(2021, 11, 1, 13, 13);
        try (MockedStatic<LocalDateTime> theMock = Mockito.mockStatic(LocalDateTime.class)) {
            theMock.when(LocalDateTime::now)
                    .thenReturn(expected);
            String greeting = GreetingUtil.constructGreeting(Gender.WOMAN, "wanita");

            assertTrue(greeting.contains("Ms."));
            assertTrue(greeting.contains("Afternoon"));
        }
    }

    @Test
    public void constructGreetingTestEveningWomen() {
        LocalDateTime expected = LocalDateTime.of(2021, 11, 1, 17, 1);
        try (MockedStatic<LocalDateTime> theMock = Mockito.mockStatic(LocalDateTime.class)) {
            theMock.when(LocalDateTime::now)
                    .thenReturn(expected);
            String greeting = GreetingUtil.constructGreeting(Gender.WOMAN, "wanita");

            assertTrue(greeting.contains("Ms."));
            assertTrue(greeting.contains("Evening"));
        }
    }


    @Test
    public void constructGreetingTestNightMenNoName() {
        LocalDateTime expected = LocalDateTime.of(2021, 11, 1, 23, 1);
        try (MockedStatic<LocalDateTime> theMock = Mockito.mockStatic(LocalDateTime.class)) {
            theMock.when(LocalDateTime::now)
                    .thenReturn(expected);
            String greeting = GreetingUtil.constructGreeting(Gender.MAN, "");

            assertTrue(greeting.contains("Mr."));
            assertTrue(greeting.contains("Night"));
            assertTrue(greeting.contains("User"));
        }
    }

}
