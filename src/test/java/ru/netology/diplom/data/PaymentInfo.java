package ru.netology.diplom.data;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PaymentInfo {
    private final String card;
    private final String month;
    private final String year;
    private final String owner;
    private final String cvv;
}
