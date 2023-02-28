package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ru.job4j.cinema.model.Hall;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HallStoreTest {
    private static BasicDataSource pool = new DBpropertiesInit().getPool();


    @Test
    public void whenFileHasNameLogo() {
        HallStore hallStore = new HallStore(pool);
        Hall actual = hallStore.findById(1);
        assertEquals("Huge Hall", actual.getDescription());
    }

    @Test
    public void whenFindByName() {
        HallStore hallStore = new HallStore(pool);
        Hall actual = hallStore.findByName("MEDIUM");
        assertEquals("Medium Hall", actual.getDescription());
    }

    @Test
    public void whenFindAllLogos() {
        HallStore hallStore = new HallStore(pool);
        List<String> expected = List.of("GRAND", "MEDIUM",
                "SMALL");
        List<String> actual = hallStore
                .findAll().stream().map(Hall::getName).toList();
        Assertions.assertEquals(expected, actual);
    }

    @AfterAll
    public static void close() throws SQLException {
        pool.close();
    }

}