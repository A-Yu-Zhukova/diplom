package ru.netology.diplom.test;

import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;

import lombok.var;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class DataTestV1 {
    QueryRunner runner;

    @BeforeEach
    void setUp() {
        runner = new QueryRunner();
    }

    @SneakyThrows
    void testOrderExist(String id) {
        String countSQL ="SELECT COUNT(*) FROM order_entity WHERE payment_id = '" + id + "';";
        System.out.println(countSQL);
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
        ) {
            long count = ((Number)runner.query(conn, countSQL, new ScalarHandler<>())).longValue();
            assertEquals(count, 1);
        }
    }

    @Test
    @SneakyThrows
    void testOrderEntitySaved() {
        String countSQL ="SELECT COUNT(*) FROM order_entity;";
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
        ) {
            long count = ((Number)runner.query(conn, countSQL, new ScalarHandler<>())).longValue();
            assertEquals(count, 4);
        }
    }

    @Test
    @SneakyThrows
    void testCreditRequestEntityApprovedSaved() {
        String countSQL ="SELECT COUNT(*) FROM credit_request_entity WHERE status = 'APPROVED';";
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
        ) {
            long count = ((Number)runner.query(conn, countSQL, new ScalarHandler<>())).longValue();
            assertEquals(count, 1);
        }
    }

    @Test
    @SneakyThrows
    void testCreditRequestEntityDeclinedSaved() {
        String countSQL ="SELECT COUNT(*) FROM credit_request_entity WHERE status = 'DECLINED';";
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
        ) {
            long count = ((Number)runner.query(conn, countSQL, new ScalarHandler<>())).longValue();
            assertEquals(count, 1);
        }
    }

    @Test
    @SneakyThrows
    void testPaymentEntityApprovedSaved() {
        String countSQL ="SELECT COUNT(*) FROM payment_entity WHERE status = 'APPROVED';";
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
        ) {
            long count = ((Number)runner.query(conn, countSQL, new ScalarHandler<>())).longValue();
            assertEquals(count, 1);
        }
    }

    @Test
    @SneakyThrows
    void testPaymentEntityDeclinedSaved() {
        String countSQL ="SELECT COUNT(*) FROM payment_entity WHERE status = 'DECLINED';";
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
        ) {
            long count = ((Number)runner.query(conn, countSQL, new ScalarHandler<>())).longValue();
            assertEquals(count, 1);
        }
    }

    @Test
    @SneakyThrows
    void validatePaymentOrderConnection() {
        String countSQL ="SELECT transaction_id FROM payment_entity;";
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
        ) {
            Set<String> ids = ((Set<String>)runner.query(conn, countSQL,
                    new ResultSetHandler<Set<String>>() {
                        public Set<String> handle(ResultSet rs) throws SQLException {
                            Set<String> rows = new HashSet<String>();
                            while (rs.next()) {
                                rows.add(rs.getString(1));
                            }
                            return rows;
                        }
                    }));
            Iterator<String> i = ids.iterator();
            while(i.hasNext()) {
                testOrderExist(i.next());
            }
        }
    }

    @Test
    @SneakyThrows
    void validateCreditOrderConnection() {
        String countSQL ="SELECT bank_id FROM credit_request_entity;";
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
        ) {
            Set<String> ids = ((Set<String>)runner.query(conn, countSQL,
                    new ResultSetHandler<Set<String>>() {
                        public Set<String> handle(ResultSet rs) throws SQLException {
                            Set<String> rows = new HashSet<String>();
                            while (rs.next()) {
                                rows.add(rs.getString(1));
                            }
                            return rows;
                        }
                    }));
            Iterator<String> i = ids.iterator();
            while(i.hasNext()) {
                testOrderExist(i.next());
            }
        }
    }
}
