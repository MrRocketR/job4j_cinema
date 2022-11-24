package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.Main;
import ru.job4j.cinema.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class UserStoreTest {

    private static BasicDataSource pool = new Main().loadPool();

    @Ignore
    @AfterClass
    public static void closeCnPool() throws SQLException {
        pool.close();
    }

    @Ignore
    @Test
    public void whenUserAdded() {
        User user = new User(0, "Tester", "tester@gmail.com", "8800", "qwerty");
        UserStore store = new UserStore(pool);
        store.add(user);
        User userInDb = store.findUserByEmailAndPassword(user.getEmail(), user.getPassword()).get();
        Assertions.assertEquals(userInDb.getEmail(), user.getEmail());
    }
}

