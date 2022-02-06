package ru.netology.diplom.test;

import com.codeborne.selenide.SelenideElement;
import ru.netology.diplom.data.PaymentInfo;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class FormTestUtils {
    PaymentInfo info;

    FormTestUtils(PaymentInfo info) { this.info = info; }

    String getExpectedText(boolean isEmpty) {
        return isEmpty ? "Поле обязательно для заполнения" : "Неверный формат";
    }
    String getDateExpectedText(boolean isEmpty) {
        return isEmpty ? "Поле обязательно для заполнения" : "Неверно указан срок действия карты";
    }

    void sendAndCheckForm(String card,
                          String month,
                          String year,
                          String owner,
                          String cvv,
                          String expected,
                          int elementIndex,
                          boolean useFirstButton) {
        if (useFirstButton) {
            $("button.button_theme_alfa-on-white").click();
        }
        else {
            $("button.button_view_extra ").click();
        }
        SelenideElement form = $("form");
        form.$$(".input__inner input.input__control").get(0).setValue(card);
        form.$$(".input__inner input.input__control").get(1).setValue(month);
        form.$$(".input__inner input.input__control").get(2).setValue(year);
        form.$$(".input__inner input.input__control").get(3).setValue(owner);
        form.$$(".input__inner input.input__control").get(4).setValue(cvv);
        form.$("button").click();
        if (expected != "") {
            form.$$(".input_type_text ").get(elementIndex).shouldHave(cssClass("input_invalid"));
            SelenideElement inputInner = form.$$(".input__inner").get(elementIndex);
            inputInner.$("span.input__sub").shouldHave(exactText(expected));
        }
    }

    void testBadCard(String card, boolean useFirstButton) {
        sendAndCheckForm(card, info.getMonth(), info.getYear(), info.getOwner(), info.getCvv(),
                getExpectedText(card.isEmpty()), 0, useFirstButton);
    }

    void testBadMonth(String month, boolean useFirstButton, boolean isInvalid) {
        sendAndCheckForm(info.getCard(), month, info.getYear(), info.getOwner(), info.getCvv(),
                (isInvalid ? getExpectedText(month.isEmpty()) : getDateExpectedText(month.isEmpty())), 1, useFirstButton);
    }

    void testBadYear(String year, boolean useFirstButton, boolean isInvalid) {
        sendAndCheckForm(info.getCard(), info.getMonth(), year, info.getOwner(), info.getCvv(),
                (isInvalid ? getExpectedText(year.isEmpty()) : getDateExpectedText(year.isEmpty())), 2, useFirstButton);
    }

    void testBadOwner(String owner, boolean useFirstButton) {
        sendAndCheckForm(info.getCard(), info.getMonth(), info.getYear(), owner, info.getCvv(),
                getExpectedText(owner.isEmpty()), 3, useFirstButton);
    }

    void testBadCvv(String cvv, boolean useFirstButton) {
        sendAndCheckForm(info.getCard(), info.getMonth(), info.getYear(), info.getOwner(), cvv,
                getExpectedText(cvv.isEmpty()), 4, useFirstButton);
    }

    void testInvalidCard(String card, boolean useFirstButton) {
        String expectedTitle = "Ошибка";
        String expectedContent = "Ошибка! Банк отказал в проведении операции.";

        sendAndCheckForm(card, info.getMonth(), info.getYear(), info.getOwner(), info.getCvv(),
                "", 0, useFirstButton);

        $(".notification").shouldBe(visible, Duration.ofSeconds(15));
        $$(".notification_visible .notification__title").get(1).shouldHave(exactText(expectedTitle));
        $$(".notification_visible .notification__content").get(1).shouldHave(exactText(expectedContent));
    }
}
