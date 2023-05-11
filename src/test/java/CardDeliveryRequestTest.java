import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryRequestTest {
    @BeforeEach
     void setup() {
        open("http://localhost:9999/");
    }
    public String generateDate(int days) {
       return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }


    @Test
    public void shouldSendRequestManual() {
        String planningDate = generateDate(4);
        $("[data-test-id='city'] input.input__control").setValue("Владивосток");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] .input__control").setValue("Петр Иванов");
        $("[data-test-id='phone'] .input__control").setValue("+77777777777");
        $("[data-test-id='agreement']").click();
        $("button.button_view_extra").click();
        $("[data-test-id='notification'] .notification__content")
                .should(appear, Duration.ofSeconds(15))
                .shouldHave(text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15));
    }

    @Test
    public void shouldSendRequestAutocompleteCity() {
        String planningDate = generateDate(4);
        $("[data-test-id='city'] input.input__control").setValue("Вл");
        $(withText("Владивосток")).click();
        $("[data-test-id='city'] input").shouldHave(value("Владивосток"));
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] .input__control").setValue("Петр Иванов");
        $("[data-test-id='phone'] .input__control").setValue("+77777777777");
        $("[data-test-id='agreement']").click();
        $("button.button_view_extra").click();
        $("[data-test-id='notification'").should(appear, Duration.ofSeconds(15));
    }
    @Test
    public void shouldSendRequestAutocompleteDate() {
        LocalDate planningDate = LocalDate.now().plusDays(150);
        LocalDate currentDate = LocalDate.parse($("[data-test-id='date'] .input__control").getValue(),
                DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='city'] input.input__control").setValue("Владивосток");
        while (currentDate.getMonthValue() != planningDate.getMonthValue()) {
            if (currentDate.getMonthValue() < planningDate.getMonthValue()) {
                $("[data-test-id='date'] .icon-button").click();
                $("[data-step='1']").click();
                $$(".calendar-input__calendar-wrapper").find(text(String.valueOf(planningDate.getDayOfMonth()))).click();
            }
                currentDate = LocalDate.parse($("[data-test-id='date'] .input__control").getValue(),
                        DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        }
        $("[data-test-id='name'] .input__control").setValue("Петр Иванов");
        $("[data-test-id='phone'] .input__control").setValue("+77777777777");
        $("[data-test-id='agreement']").click();
        $("button.button_view_extra").click();
        $("[data-test-id='notification'] .notification__content")
                .should(appear, Duration.ofSeconds(15))
                .shouldHave(text("Встреча успешно забронирована на " + planningDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))), Duration.ofSeconds(15));

    }
}
