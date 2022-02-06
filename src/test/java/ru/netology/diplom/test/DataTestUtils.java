package ru.netology.diplom.test;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataTestUtils {
    Connection conn;
    QueryRunner runner;

    DataTestUtils(Connection conn, QueryRunner runner) {
        this.conn = conn;
        this.runner = runner;
    }

    @SneakyThrows
    void testOrderExist(String id) {
        String countSQL ="SELECT COUNT(*) FROM order_entity WHERE payment_id = '" + id + "';";
        System.out.println(countSQL);
        long count = ((Number)runner.query(conn, countSQL, new ScalarHandler<>())).longValue();
        assertEquals(count, 1);
    }
}
