package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.model.User;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;



public class UserStoreTest {

    private static BasicDataSource pool;

    @BeforeAll
    public static void initSource() {
        try (InputStream input = new FileInputStream("src/test/resources/db.properties"))  {
            Properties cfg  = new Properties();
            cfg.load(input);
            Class.forName(cfg.getProperty("jdbc.driver"));
            pool = new BasicDataSource();
            pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
            pool.setUrl(cfg.getProperty("jdbc.url"));
            pool.setUsername(cfg.getProperty("jdbc.username"));
            pool.setPassword(cfg.getProperty("jdbc.password"));
            pool.setMinIdle(5);
            pool.setMaxIdle(10);
            pool.setMaxOpenPreparedStatements(100);

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void whenUserAdded() {
        User user = new User(0, "Tester32", "tester32@gmail.com", "8800", "qwerty");
        UserStore store = new UserStore(pool);
        store.add(user);
        User userInDb = store.findUserByEmailAndPassword(user.getEmail(), user.getPassword()).get();
        Assertions.assertEquals(userInDb.getEmail(), user.getEmail());
    }


    @After
    public void wheDone() throws SQLException {
        pool.close();
    }
}

