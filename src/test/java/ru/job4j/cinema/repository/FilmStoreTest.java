package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;


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
    private static BasicDataSource pool = new DBpropertiesInit().getPool();

    public static void main(String[] args) {
        System.out.println(pool.getDriverClassName());
    }

    @Test
    public void poops() {
        FileStore fileStoreTest = new FileStore(pool);
        System.out.println(fileStoreTest.findAll());
    }


}