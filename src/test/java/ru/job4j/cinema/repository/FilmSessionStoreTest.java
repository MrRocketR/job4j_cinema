package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ru.job4j.cinema.model.FilmSession;

import java.sql.SQLException;
import java.util.List;



public class FilmSessionStoreTest {
    private static BasicDataSource pool = new DBpropertiesInit().getPool();


    @Test
    public void whenFindById() {
        FilmSessionStore filmSessionStore = new FilmSessionStore(pool);
        FilmSession filmSession = filmSessionStore.findById(1);
        Assertions.assertEquals(1, filmSession.getFilmId());

    }

    @Test
    public void whenFindByFilmId() {
        FilmSessionStore filmSessionStore = new FilmSessionStore(pool);
        List<Integer> expected = List.of(13, 14);
        List<Integer> actual = filmSessionStore
                .findByFilmId(4).stream().map(FilmSession::getId).toList();
        Assertions.assertEquals(expected, actual);

    }

    @AfterAll
    public static void close() throws SQLException {
        pool.close();
    }

}