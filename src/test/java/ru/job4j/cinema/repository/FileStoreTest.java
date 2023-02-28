package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.model.File;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class FileStoreTest {
    private static BasicDataSource pool = new DBpropertiesInit().getPool();


    @Test
    public void whenFileHasNameLogo() {
        FileStore fileStoreTest = new FileStore(pool);
        File file = fileStoreTest.findById(1);
        assertEquals("logo", file.getName());
    }

    @Test
    public void whenFindByName() {
        FileStore fileStoreTest = new FileStore(pool);
        File file = fileStoreTest.findByName("GRAND");
        assertEquals("GRAND", file.getName());
    }

    @Test
    public void whenFindAllLogos() {
        FileStore fileStoreTest = new FileStore(pool);
        List<String> expected = List.of("logo", "comedy1",
                "action1", "drama1",
                "horror1", "GRAND",
                "MEDIUM", "SMALL");
        List<String> actual = fileStoreTest
                .findAll().stream().map(File::getName).toList();
        Assertions.assertEquals(expected, actual);
    }

    @AfterAll
    public static void close() throws SQLException {
        pool.close();
    }


}