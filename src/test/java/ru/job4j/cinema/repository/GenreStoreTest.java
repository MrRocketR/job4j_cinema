package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ru.job4j.cinema.model.Genre;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GenreStoreTest {

    private static BasicDataSource pool = new DBpropertiesInit().getPool();


    @Test
    public void whenFileHasNameLogo() {
        GenreStore genreStore = new GenreStore(pool);
        Genre actual = genreStore.findById(1);
        assertEquals("action", actual.getName());
    }

    @Test
    public void whenFindByName() {
        GenreStore genreStore = new GenreStore(pool);
        Genre actual = genreStore.findByName("comedy");
        assertEquals("comedy", actual.getName());
    }

    @Test
    public void whenFindAllLogos() {
        GenreStore genreStore = new GenreStore(pool);
        List<String> expected = List.of("action", "comedy",
                "drama", "horror");
        List<String> actual = genreStore
                .findAll().stream().map(Genre::getName).toList();
        Assertions.assertEquals(expected, actual);
    }

    @AfterAll
    public static void close() throws SQLException {
        pool.close();
    }

}