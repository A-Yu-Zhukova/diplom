package ru.netology.diplom.test;

import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;

import lombok.var;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.sql.Connection;
import java.sql.DriverManager;

public class DataTestV1 {
    QueryRunner runner;

    @BeforeEach
    void setUp() {
        runner = new QueryRunner();
    }

    @Test
    @SneakyThrows
    void testRequestSaved() {
        String countSQL ="SELECT COUNT(*) FROM app;";
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
        ) {
            ScalarHandler count = runner.query(conn, countSQL, new ScalarHandler<>());

        }
    }
}
