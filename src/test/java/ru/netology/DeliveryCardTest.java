package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class DeliveryCardTest {

    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.BASIC_ISO_DATE.ofPattern(pattern));
    }

    @Test
    public void shouldBeValidDataTest() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Новосибирск");
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Ольга Тайлакова");
        $("[data-test-id='phone'] input").setValue("+79537924254");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15, 15))
                .shouldBe(Condition.exactText("Встреча успешно забронирована на " + planningDate));
    }
}
