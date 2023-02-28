package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ru.job4j.cinema.model.Film;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilmStoreTest {


    private static BasicDataSource pool = new DBpropertiesInit().getPool();

    @Test
    public void whenFindFilmById() {
        FilmStore filmStore = new FilmStore(pool);
        Film melancholia = filmStore.findById(3);
        assertEquals("Melancholia", melancholia.getName());
    }

    @Test
    public void whenFindFilmByName() {
        FilmStore filmStore = new FilmStore(pool);
        Film dracula = filmStore.findByName("Dracula");
        assertEquals("Dracula", dracula.getName());
    }

    @Test
    public void whenFindAll() {
        FilmStore filmStore = new FilmStore(pool);
        List<String> expected = List.of("Rock and Dwayne", "Old Danny",
                "Melancholia", "Dracula");
        List<String> actual = filmStore
                .findAll().stream().map(Film::getName).toList();
        Assertions.assertEquals(expected, actual);

    }

    @AfterAll
    public static void close() throws SQLException {
        pool.close();
    }
}
