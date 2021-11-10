package ru.netology.diplom.test;

import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;

import lombok.var;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class DataTestV1 {
    QueryRunner runner;

    @BeforeEach
    void setUp() {
        runner = new QueryRunner();
    }

    @Test
    @SneakyThrows
    void testOrderEntitySaved() {
        String countSQL ="SELECT COUNT(*) FROM order_entity;";
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
        ) {
            long count = ((Number)runner.query(conn, countSQL, new ScalarHandler<>())).longValue();
            assertNotEquals(count, 0);
        }
    }

    @Test
    @SneakyThrows
    void testCreditRequestEntitySaved() {
        String countSQL ="SELECT COUNT(*) FROM credit_request_entity;";
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
        ) {
            long count = ((Number)runner.query(conn, countSQL, new ScalarHandler<>())).longValue();
            assertNotEquals(count, 0);
        }
    }

    @Test
    @SneakyThrows
    void testPaymentEntitySaved() {
        String countSQL ="SELECT COUNT(*) FROM payment_entity;";
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
        ) {
            long count = ((Number)runner.query(conn, countSQL, new ScalarHandler<>())).longValue();
            assertNotEquals(count, 0);
        }
    }
}
