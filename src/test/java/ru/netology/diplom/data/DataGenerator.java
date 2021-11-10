package ru.netology.diplom.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {
    private DataGenerator() {
    }

    public static class PaymentRequest {
        private PaymentRequest() {
        }

        public static PaymentInfo generateByCard(String locale) {
            Faker faker = new Faker(new Locale("ru"));
            return new PaymentInfo(
                    "4444 4444 4444 4441",
                    "08",
                    "22",
                    faker.name().fullName(),
                    "111"
            );
        }

        public static String getShortCard() {
            return "123";
        }

        public static String getRejectedCard() {
            return "4444 4444 4444 4443";
        }

        public static String getWrongCard() {
            return "4444 4444 4444 1234";
        }

        public static String getEmptyCard() {
            return "";
        }

        public static String getOverflowMonth() {
            return "18";
        }

        public static String getUnderflowMonth() {
            return "00";
        }

        public static String getShortMonth() {
            return "8";
        }

        public static String getEmptyMonth() {
            return "";
        }

        public static String getWrongYear() {
            return "99";
        }

        public static String getShortYear() {
            return "1";
        }

        public static String getEmptyYear() {
            return "";
        }

        public static String getWrongOwner() {
            return "6457654";
        }

        public static String getEmptyOwner() {
            return "";
        }

        public static String getShortCvv() {
            return "1";
        }

        public static String getWrongCvv() {
            return "666";
        }

        public static String getEmptyCvv() {
            return "";
        }
    }
}