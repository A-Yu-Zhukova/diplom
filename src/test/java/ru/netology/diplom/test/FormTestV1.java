package ru.netology.diplom.test;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.diplom.data.PaymentInfo;
import ru.netology.diplom.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class FormTestV1 {
    static PaymentInfo info;

    @BeforeAll
    static void setUpInfo() {
        info = DataGenerator.PaymentRequest.generateByCard("ru");
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:8080");
    }

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

    @Test
    void testShortCard() {
        testBadCard(DataGenerator.PaymentRequest.getShortCard(), true);
    }

    @Test
    void testWrongCard() {
        testInvalidCard(DataGenerator.PaymentRequest.getWrongCard(), true);
    }

    @Test
    void testEmptyCard() {
        testBadCard(DataGenerator.PaymentRequest.getEmptyCard(), true);
    }

    @Test
    void testOverflowMonth() {
        testBadMonth(DataGenerator.PaymentRequest.getOverflowMonth(), true, false);
    }

    @Test
    void testUnderflowMonth() {
        testBadMonth(DataGenerator.PaymentRequest.getUnderflowMonth(), true, false);
    }

    @Test
    void testShortMonth() {
        testBadMonth(DataGenerator.PaymentRequest.getShortMonth(), true, true);
    }

    @Test
    void testEmptyMonth() {
        testBadMonth(DataGenerator.PaymentRequest.getEmptyMonth(), true, false);
    }

    @Test
    void testWrongYear() {
        testBadYear(DataGenerator.PaymentRequest.getWrongYear(), true, false);
    }

    @Test
    void testShortYear() {
        testBadYear(DataGenerator.PaymentRequest.getShortYear(), true, true);
    }

    @Test
    void testEmptyYear() {
        testBadYear(DataGenerator.PaymentRequest.getEmptyYear(), true, false);
    }

    @Test
    void testWrongOwner() {
        testBadOwner(DataGenerator.PaymentRequest.getWrongOwner(), true);
    }

    @Test
    void testEmptyOwner() {
        testBadOwner(DataGenerator.PaymentRequest.getEmptyOwner(), true);
    }

    /* @Test
    void testWrongCvv() {
        testBadCvv(DataGenerator.PaymentRequest.getWrongCvv(), true);
    }*/

    @Test
    void testShortCvv() {
        testBadCvv(DataGenerator.PaymentRequest.getShortCvv(), true);
    }

    @Test
    void testEmptyCvv() {
        testBadCvv(DataGenerator.PaymentRequest.getEmptyCvv(), true);
    }

///////////////////////////


    @Test
    void testShortCardCredit() {
        testBadCard(DataGenerator.PaymentRequest.getShortCard(), false);
    }

    @Test
    void testWrongCardCredit() {
        testInvalidCard(DataGenerator.PaymentRequest.getWrongCard(), false);
    }

    @Test
    void testEmptyCardCredit() {
        testBadCard(DataGenerator.PaymentRequest.getEmptyCard(), false);
    }

    @Test
    void testOverflowMonthCredit() {
        testBadMonth(DataGenerator.PaymentRequest.getOverflowMonth(), false, false);
    }

    @Test
    void testUnderflowMonthCredit() {
        testBadMonth(DataGenerator.PaymentRequest.getUnderflowMonth(), false, false);
    }

    @Test
    void testShortMonthCredit() {
        testBadMonth(DataGenerator.PaymentRequest.getShortMonth(), false, true);
    }

    @Test
    void testEmptyMonthCredit() {
        testBadMonth(DataGenerator.PaymentRequest.getEmptyMonth(), false, false);
    }

    @Test
    void testWrongYearCredit() {
        testBadYear(DataGenerator.PaymentRequest.getWrongYear(), false, false);
    }

    @Test
    void testShortYearCredit() {
        testBadYear(DataGenerator.PaymentRequest.getShortYear(), false, true);
    }

    @Test
    void testEmptyYearCredit() {
        testBadYear(DataGenerator.PaymentRequest.getEmptyYear(), false, false);
    }

    @Test
    void testWrongOwnerCredit() {
        testBadOwner(DataGenerator.PaymentRequest.getWrongOwner(), false);
    }

    @Test
    void testEmptyOwnerCredit() {
        testBadOwner(DataGenerator.PaymentRequest.getEmptyOwner(), false);
    }
/*
    @Test
    void testWrongCvvCredit() {
        testBadCvv(DataGenerator.PaymentRequest.getWrongCvv(), false);
    }*/

    @Test
    void testShortCvvCredit() {
        testBadCvv(DataGenerator.PaymentRequest.getShortCvv(), false);
    }

    @Test
    void testEmptyCvvCredit() {
        testBadCvv(DataGenerator.PaymentRequest.getEmptyCvv(), false);
    }


    @Test
    void testApprovedCard() {
        String expectedTitle = "Успешно";
        String expectedContent = "Операция одобрена Банком.";

        sendAndCheckForm(info.getCard(), info.getMonth(), info.getYear(), info.getOwner(), info.getCvv(),
                "", 0, true);

        $(".notification").shouldBe(visible, Duration.ofSeconds(15));
        $$(".notification_visible .notification__title").get(0).shouldHave(exactText(expectedTitle));
        $$(".notification_visible .notification__content").get(0).shouldHave(exactText(expectedContent));
        $$(".notification_visible .notification__title").get(1).shouldBe(hidden);
    }

    @Test
    void testApprovedCardCredit() {
        String expectedTitle = "Успешно";
        String expectedContent = "Операция одобрена Банком.";

        sendAndCheckForm(info.getCard(), info.getMonth(), info.getYear(), info.getOwner(), info.getCvv(),
                "", 0, false);

        $(".notification").shouldBe(visible, Duration.ofSeconds(15));
        $$(".notification_visible .notification__title").get(0).shouldHave(exactText(expectedTitle));
        $$(".notification_visible .notification__content").get(0).shouldHave(exactText(expectedContent));
        $$(".notification_visible .notification__title").get(1).shouldBe(hidden);
    }

    @Test
    void testRejectedCard() {
        testInvalidCard(DataGenerator.PaymentRequest.getRejectedCard(), true);
    }

    @Test
    void testRejectedCardCredit() {
        testInvalidCard(DataGenerator.PaymentRequest.getRejectedCard(), false);
    }

}
