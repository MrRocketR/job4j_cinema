package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;

import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.Main;
import ru.job4j.cinema.model.Film;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;




public class FilmStoreTest {
    private static BasicDataSource pool = new Main().loadPool();

    @Ignore
    @AfterEach
    public void wipeTable() throws SQLException {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("TRUNCATE table tickets")
        ) {
            ps.execute();
        }
    }

    @Ignore
    @AfterClass
    public static void closeCnPool() throws SQLException {
        pool.close();
    }


    @Ignore
    @Test
    public void whenFilmAdded() {
        FilmStore store = new FilmStore(pool);
        Film film = new Film(0, "Testers", "Movie for True QA!", new byte[2]);
        store.add(film);
        Film stored = store.findById(film.getId());
        Assertions.assertEquals(film.getName(), stored.getName());

    }
    @Ignore
    @Test
    public void whenFilmAddedAndUpdates() {
        FilmStore store = new FilmStore(pool);
        Film film = new Film(0, "Testers", "Movie for True QA!", new byte[2]);
        store.add(film);
        store.update(new Film(film.getId(), "Testers3DD", "Movie for True QA!", new byte[2]));
        Film stored = store.findById(film.getId());
        Assertions.assertEquals(film.getName(), stored.getName());

    }
}