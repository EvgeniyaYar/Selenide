import com.codeborne.selenide.Condition;
import com.codeborne.selenide.selector.ByText;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryRequestTest {
    @BeforeAll
    static void setup() {
        open("http://localhost:9999/");
    }

    @Test
    public void shouldSendRequestManual() {
        $("[data-test-id='city'] input.input__control").setValue("Владивосток");
        $("[data-test-id='name'] .input__control").setValue("Петр Иванов");
        $("[data-test-id='phone'] .input__control").setValue("+77777777777");
        $("[data-test-id='agreement']").click();
        $("button.button_view_extra").click();
        $("[data-test-id='notification'").should(appear, Duration.ofSeconds(15));
    }

    @Test
    public void shouldSendRequestAutocompleteCity() {
        $("[data-test-id='city'] input.input__control").setValue("Вл");
        $(withText("Владивосток")).click();
        $("[data-test-id='name'] .input__control").setValue("Петр Иванов");
        $("[data-test-id='phone'] .input__control").setValue("+77777777777");
        $("[data-test-id='agreement']").click();
        $("button.button_view_extra").click();
        $("[data-test-id='notification'").should(appear, Duration.ofSeconds(15));
    }

//    @Test
//    public void shouldSendRequestAutocompleteDate() {
//        $("[data-test-id='city'] input.input__control").setValue("Владивосток");
//
////       $("[data-test-id='date'] input.calendar-input__native-control").click();
////        $("[data-test-id='date'] input.calendar-input__native-control").sendKeys(Keys.chord(Keys.DELETE));
////        $("[data-test-id='date'] input.calendar-input__native-control").click();
//        $("[data-test-id='date'] button.icon-button").click();
//        $("[data-test-id='date'] input.calendar-input__native-control td[value='2023-05-18']").shouldBe(visible, Duration.ofSeconds(20));da//
//        $("[data-test-id='date'] input.calendar-input__native-control td[value='2023-05-18']").click();
////        $("[data-test-id='date'] input.input__control td[value='18.05.2023']").shouldBe(visible, Duration.ofSeconds(20));
//
////        $("[data-test-id='date'] .calendar-input__native-control").shouldBe(visible, Duration.ofSeconds(20));
////      $("[data-test-id='date'] .calendar-input__native-control td[value='2023-05-18").click();
//        $("[data-test-id='name'] .input__control").setValue("Петр Иванов");
//        $("[data-test-id='phone'] .input__control").setValue("+77777777777");
//        $("[data-test-id='agreement']").click();
//        $("button.button_view_extra").click();
//        $("[data-test-id='notification'").should(appear, Duration.ofSeconds(15));
//    }

}
