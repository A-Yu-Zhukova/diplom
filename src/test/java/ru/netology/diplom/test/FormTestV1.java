package ru.netology.diplom.test;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.diplom.data.PaymentInfo;
import ru.netology.diplom.data.DataGenerator;

import java.text.Normalizer;
import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class FormTestV1 {
    static PaymentInfo info;
    static FormTestUtils utils;

    @BeforeAll
    static void setUpInfo() {
        info = DataGenerator.PaymentRequest.generateByCard("ru");
        utils = new FormTestUtils(info);
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:8080");
    }

    @Test
    void testShortCard() {
        utils.testBadCard(DataGenerator.PaymentRequest.getShortCard(), true);
    }

    @Test
    void testWrongCard() {
        utils.testInvalidCard(DataGenerator.PaymentRequest.getWrongCard(), true);
    }

    @Test
    void testEmptyCard() {
        utils.testBadCard(DataGenerator.PaymentRequest.getEmptyCard(), true);
    }

    @Test
    void testOverflowMonth() {
        utils.testBadMonth(DataGenerator.PaymentRequest.getOverflowMonth(), true, false);
    }

    @Test
    void testUnderflowMonth() {
        utils.testBadMonth(DataGenerator.PaymentRequest.getUnderflowMonth(), true, false);
    }

    @Test
    void testShortMonth() {
        utils.testBadMonth(DataGenerator.PaymentRequest.getShortMonth(), true, true);
    }

    @Test
    void testEmptyMonth() {
        utils.testBadMonth(DataGenerator.PaymentRequest.getEmptyMonth(), true, false);
    }

    @Test
    void testWrongYear() {
        utils.testBadYear(DataGenerator.PaymentRequest.getWrongYear(), true, false);
    }

    @Test
    void testShortYear() {
        utils.testBadYear(DataGenerator.PaymentRequest.getShortYear(), true, true);
    }

    @Test
    void testEmptyYear() {
        utils.testBadYear(DataGenerator.PaymentRequest.getEmptyYear(), true, false);
    }

    @Test
    void testWrongOwner() {
        utils.testBadOwner(DataGenerator.PaymentRequest.getWrongOwner(), true);
    }

    @Test
    void testEmptyOwner() {
        utils.testBadOwner(DataGenerator.PaymentRequest.getEmptyOwner(), true);
    }

    /* @Test
    void testWrongCvv() {
        testBadCvv(DataGenerator.PaymentRequest.getWrongCvv(), true);
    }*/

    @Test
    void testShortCvv() {
        utils.testBadCvv(DataGenerator.PaymentRequest.getShortCvv(), true);
    }

    @Test
    void testEmptyCvv() {
        utils.testBadCvv(DataGenerator.PaymentRequest.getEmptyCvv(), true);
    }

///////////////////////////


    @Test
    void testShortCardCredit() {
        utils.testBadCard(DataGenerator.PaymentRequest.getShortCard(), false);
    }

    @Test
    void testWrongCardCredit() {
        utils.testInvalidCard(DataGenerator.PaymentRequest.getWrongCard(), false);
    }

    @Test
    void testEmptyCardCredit() {
        utils.testBadCard(DataGenerator.PaymentRequest.getEmptyCard(), false);
    }

    @Test
    void testOverflowMonthCredit() {
        utils.testBadMonth(DataGenerator.PaymentRequest.getOverflowMonth(), false, false);
    }

    @Test
    void testUnderflowMonthCredit() {
        utils.testBadMonth(DataGenerator.PaymentRequest.getUnderflowMonth(), false, false);
    }

    @Test
    void testShortMonthCredit() {
        utils.testBadMonth(DataGenerator.PaymentRequest.getShortMonth(), false, true);
    }

    @Test
    void testEmptyMonthCredit() {
        utils.testBadMonth(DataGenerator.PaymentRequest.getEmptyMonth(), false, false);
    }

    @Test
    void testWrongYearCredit() {
        utils.testBadYear(DataGenerator.PaymentRequest.getWrongYear(), false, false);
    }

    @Test
    void testShortYearCredit() {
        utils.testBadYear(DataGenerator.PaymentRequest.getShortYear(), false, true);
    }

    @Test
    void testEmptyYearCredit() {
        utils.testBadYear(DataGenerator.PaymentRequest.getEmptyYear(), false, false);
    }

    @Test
    void testWrongOwnerCredit() {
        utils.testBadOwner(DataGenerator.PaymentRequest.getWrongOwner(), false);
    }

    @Test
    void testEmptyOwnerCredit() {
        utils.testBadOwner(DataGenerator.PaymentRequest.getEmptyOwner(), false);
    }
/*
    @Test
    void testWrongCvvCredit() {
        testBadCvv(DataGenerator.PaymentRequest.getWrongCvv(), false);
    }*/

    @Test
    void testShortCvvCredit() {
        utils.testBadCvv(DataGenerator.PaymentRequest.getShortCvv(), false);
    }

    @Test
    void testEmptyCvvCredit() {
        utils.testBadCvv(DataGenerator.PaymentRequest.getEmptyCvv(), false);
    }


    @Test
    void testApprovedCard() {
        String expectedTitle = "Успешно";
        String expectedContent = "Операция одобрена Банком.";

        utils.sendAndCheckForm(info.getCard(), info.getMonth(), info.getYear(), info.getOwner(), info.getCvv(),
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

        utils.sendAndCheckForm(info.getCard(), info.getMonth(), info.getYear(), info.getOwner(), info.getCvv(),
                "", 0, false);

        $(".notification").shouldBe(visible, Duration.ofSeconds(15));
        $$(".notification_visible .notification__title").get(0).shouldHave(exactText(expectedTitle));
        $$(".notification_visible .notification__content").get(0).shouldHave(exactText(expectedContent));
        $$(".notification_visible .notification__title").get(1).shouldBe(hidden);
    }

    @Test
    void testRejectedCard() {
        utils.testInvalidCard(DataGenerator.PaymentRequest.getRejectedCard(), true);
    }

    @Test
    void testRejectedCardCredit() {
        utils.testInvalidCard(DataGenerator.PaymentRequest.getRejectedCard(), false);
    }

}
