package com.example.starter;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Slf4j
@SpringBootTest
class JdbcAPITests extends JdbcTest {

    @Test
    void contextLoads() {
    }

    @Test
    @SneakyThrows
    public void select() {
        try (final PreparedStatement statement = connection.prepareStatement("SELECT * FROM customer where name = ?")) {
            statement.setString(1, "小昭");
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("cust_id");
                String name = resultSet.getString("name");
                Integer age = resultSet.getInt("age");
                log.info("id: {}, name:{}, age:{}", id, name, age);
            }
        }
    }


    @Test
    @SneakyThrows
    public void insert() {
        try (final PreparedStatement preparedStatement
                     = connection.prepareStatement("INSERT INTO customer (`cust_id`, `name`, `age`)  VALUES ( ?,?,?)")) {
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "小昭");
            preparedStatement.setInt(3, 16);
            final boolean result = preparedStatement.execute();
            log.info("result: {}", result);
        }
    }

    @Test
    @SneakyThrows
    public void delete() {
        try (final PreparedStatement statement = connection.prepareStatement("delete FROM customer where name = ?")) {
            statement.setString(1, "小昭");
            final boolean result = statement.execute();
            log.info("result: {}", result);
        }
    }
}