package ru.netology.diplom.test;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.diplom.data.PaymentInfo;
import ru.netology.diplom.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

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
        form.$$(".input_type_text ").get(elementIndex).shouldHave(cssClass("input_invalid"));
        SelenideElement inputInner = form.$$(".input__inner").get(elementIndex);
        inputInner.$("span.input__sub").shouldHave(exactText(expected));
    }

    void testBadCard(String card, boolean useFirstButton) {
        sendAndCheckForm(card, info.getMonth(), info.getYear(), info.getOwner(), info.getCvv(),
                getExpectedText(card.isEmpty()), 0, useFirstButton);
    }

    void testBadMonth(String month, boolean useFirstButton) {
        sendAndCheckForm(info.getCard(), month, info.getYear(), info.getOwner(), info.getCvv(),
                getExpectedText(month.isEmpty()), 1, useFirstButton);
    }

    void testBadYear(String year, boolean useFirstButton) {
        sendAndCheckForm(info.getCard(), info.getMonth(), year, info.getOwner(), info.getCvv(),
                getExpectedText(year.isEmpty()), 2, useFirstButton);
    }

    void testBadOwner(String owner, boolean useFirstButton) {
        sendAndCheckForm(info.getCard(), info.getMonth(), info.getYear(), owner, info.getCvv(),
                getExpectedText(owner.isEmpty()), 3, useFirstButton);
    }

    void testBadCvv(String cvv, boolean useFirstButton) {
        sendAndCheckForm(info.getCard(), info.getMonth(), info.getCvv(), info.getOwner(), cvv,
                getExpectedText(cvv.isEmpty()), 4, useFirstButton);
    }

    @Test
    void testShortCard() {
        testBadCard(DataGenerator.PaymentRequest.getShortCard(), true);
    }

    @Test
    void testWrongCard() {
        testBadCard(DataGenerator.PaymentRequest.getWrongCard(), true);
    }

    @Test
    void testEmptyCard() {
        testBadCard(DataGenerator.PaymentRequest.getEmptyCard(), true);
    }

    @Test
    void testOverflowMonth() {
        testBadMonth(DataGenerator.PaymentRequest.getOverflowMonth(), true);
    }

    @Test
    void testUnderflowMonth() {
        testBadMonth(DataGenerator.PaymentRequest.getUnderflowMonth(), true);
    }

    @Test
    void testShortMonth() {
        testBadMonth(DataGenerator.PaymentRequest.getShortMonth(), true);
    }

    @Test
    void testEmptyMonth() {
        testBadMonth(DataGenerator.PaymentRequest.getEmptyMonth(), true);
    }

    @Test
    void testWrongYear() {
        testBadYear(DataGenerator.PaymentRequest.getWrongYear(), true);
    }

    @Test
    void testShortYear() {
        testBadYear(DataGenerator.PaymentRequest.getShortYear(), true);
    }

    @Test
    void testEmptyYear() {
        testBadYear(DataGenerator.PaymentRequest.getEmptyYear(), true);
    }

    @Test
    void testWrongOwner() {
        testBadOwner(DataGenerator.PaymentRequest.getWrongOwner(), true);
    }

    @Test
    void testEmptyOwner() {
        testBadOwner(DataGenerator.PaymentRequest.getEmptyOwner(), true);
    }

    @Test
    void testWrongCvv() {
        testBadCvv(DataGenerator.PaymentRequest.getWrongCvv(), true);
    }

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
        testBadCard(DataGenerator.PaymentRequest.getWrongCard(), false);
    }

    @Test
    void testEmptyCardCredit() {
        testBadCard(DataGenerator.PaymentRequest.getEmptyCard(), false);
    }

    @Test
    void testOverflowMonthCredit() {
        testBadMonth(DataGenerator.PaymentRequest.getOverflowMonth(), false);
    }

    @Test
    void testUnderflowMonthCredit() {
        testBadMonth(DataGenerator.PaymentRequest.getUnderflowMonth(), false);
    }

    @Test
    void testShortMonthCredit() {
        testBadMonth(DataGenerator.PaymentRequest.getShortMonth(), false);
    }

    @Test
    void testEmptyMonthCredit() {
        testBadMonth(DataGenerator.PaymentRequest.getEmptyMonth(), false);
    }

    @Test
    void testWrongYearCredit() {
        testBadYear(DataGenerator.PaymentRequest.getWrongYear(), false);
    }

    @Test
    void testShortYearCredit() {
        testBadYear(DataGenerator.PaymentRequest.getShortYear(), false);
    }

    @Test
    void testEmptyYearCredit() {
        testBadYear(DataGenerator.PaymentRequest.getEmptyYear(), false);
    }

    @Test
    void testWrongOwnerCredit() {
        testBadOwner(DataGenerator.PaymentRequest.getWrongOwner(), false);
    }

    @Test
    void testEmptyOwnerCredit() {
        testBadOwner(DataGenerator.PaymentRequest.getEmptyOwner(), false);
    }

    @Test
    void testWrongCvvCredit() {
        testBadCvv(DataGenerator.PaymentRequest.getWrongCvv(), false);
    }

    @Test
    void testShortCvvCredit() {
        testBadCvv(DataGenerator.PaymentRequest.getShortCvv(), false);
    }

    @Test
    void testEmptyCvvCredit() {
        testBadCvv(DataGenerator.PaymentRequest.getEmptyCvv(), false);
    }



    /*
    @Test
    void testCorrect() {
        String meetDate = DataGenerator.PaymentRequest.getMeetDate(3);
        String expected = "Встреча успешно запланирована на " + meetDate;
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue(info.getCity());
        form.$("[data-test-id=date] .input__control").sendKeys(Keys.CONTROL + "A");
        form.$("[data-test-id=date] .input__control").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(meetDate);
        form.$("[data-test-id=name] input").setValue(info.getName());
        form.$("[data-test-id=phone] input").setValue(info.getPhone());
        form.$("[data-test-id=agreement]").click();
        form.$("button.button_view_extra").click();

        $("[data-test-id=success-notification]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=success-notification] .notification__content").shouldHave(exactText(expected));
    }

    @Test
    void testReplan() {
        String meetDate = DataGenerator.PaymentRequest.getMeetDate(3);
        String expected = "Встреча успешно запланирована на " + meetDate;
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue(info.getCity());
        form.$("[data-test-id=date] .input__control").sendKeys(Keys.CONTROL + "A");
        form.$("[data-test-id=date] .input__control").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(meetDate);
        form.$("[data-test-id=name] input").setValue(info.getName());
        form.$("[data-test-id=phone] input").setValue(info.getPhone());
        form.$("[data-test-id=agreement]").click();
        form.$("button.button_view_extra").click();

        $("[data-test-id=replan-notification]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=replan-notification] button").click();

        $("[data-test-id=success-notification]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=success-notification] .notification__content").shouldHave(exactText(expected));
    }*/
/*
    @Test
    void testBadDate() {
        String meetDate = DataGenerator.PaymentRequest.getMeetDate(1);
        String expected = "Заказ на выбранную дату невозможен";
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue(info.getCity());
        form.$("[data-test-id=date] .input__control").sendKeys(Keys.CONTROL + "A");
        form.$("[data-test-id=date] .input__control").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] .input__control").setValue(meetDate);
        form.$("[data-test-id=name] input").setValue(info.getName());
        form.$("[data-test-id=phone] input").setValue(info.getPhone());
        form.$("[data-test-id=agreement]").click();
        form.$("button.button_view_extra").click();
        $("[data-test-id=date] .input").shouldHave(cssClass("input_invalid"));
        $("[data-test-id=date] .input_invalid .input__inner .input__sub").shouldHave(exactText(expected));
    }

    @Test
    void testBadName() {
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue(info.getCity());
        form.$("[data-test-id=date] .input__control").sendKeys(Keys.CONTROL + "A");
        form.$("[data-test-id=date] .input__control").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(DataGenerator.PaymentRequest.getMeetDate(3));
        form.$("[data-test-id=phone] input").setValue(info.getPhone());
        form.$("[data-test-id=name] input").setValue(DataGenerator.PaymentRequest.getBadName());
        form.$("[data-test-id=agreement]").click();
        form.$("button.button_view_extra").click();
        $("[data-test-id=name]").shouldHave(cssClass("input_invalid"));
        $("[data-test-id=name].input_invalid .input__inner .input__sub").shouldHave(exactText(expected));
    }

    @Test
    void testBadAgreement() {
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue(info.getCity());
        form.$("[data-test-id=date] .input__control").sendKeys(Keys.CONTROL + "A");
        form.$("[data-test-id=date] .input__control").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(DataGenerator.PaymentRequest.getMeetDate(3));
        form.$("[data-test-id=name] input").setValue(info.getName());
        form.$("[data-test-id=phone] input").setValue(info.getPhone());
        form.$("button.button_view_extra").click();
        $("[data-test-id=agreement]").shouldHave(cssClass("input_invalid"));
    }

    @Test
    void testBadPhone() {
        String expected = "Поле обязательно для заполнения";
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue(info.getCity());
        form.$("[data-test-id=date] .input__control").sendKeys(Keys.CONTROL + "A");
        form.$("[data-test-id=date] .input__control").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(DataGenerator.PaymentRequest.getMeetDate(3));
        form.$("[data-test-id=name] input").setValue(info.getName());
        form.$("[data-test-id=phone] input").setValue(DataGenerator.PaymentRequest.getBadPhone());
        form.$("[data-test-id=agreement]").click();
        form.$("button.button_view_extra").click();
        $("[data-test-id=phone]").shouldHave(cssClass("input_invalid"));
        $("[data-test-id=phone].input_invalid .input__inner .input__sub").shouldHave(exactText(expected));
    }*/
}
