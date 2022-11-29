package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;

import org.junit.After;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.model.Film;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;


public class FilmStoreTest {
    private static BasicDataSource pool;

    @BeforeAll
    public static void initSource() {
        try (InputStream input = new FileInputStream("src/test/resources/db.properties")) {
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
    public void whenFilmAdded() {
        FilmStore store = new FilmStore(pool);
        Film film = new Film(0, "Testers", "Movie for True QA!", new byte[2]);
        store.add(film);
        Film stored = store.findById(film.getId());
        Assertions.assertEquals(film.getName(), stored.getName());

    }
    @Test
    public void whenFilmAddedAndUpdates() {
        FilmStore store = new FilmStore(pool);
        Film film = new Film(0, "Testers2", "Movie for True QA!", new byte[2]);
        store.add(film);
        store.update(new Film(film.getId(), "Testers3DD", "Movie for True QA!", new byte[2]));
        Film stored = store.findById(film.getId());
        Assertions.assertEquals("Testers3DD", stored.getName());

    }

    @After
    public void wheDone() throws SQLException {
        pool.close();
    }
}