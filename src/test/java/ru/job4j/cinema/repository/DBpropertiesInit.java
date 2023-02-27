package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;



public class DBpropertiesInit {

    private BasicDataSource pool;

    public DBpropertiesInit() {
        initSource();
    }
     public BasicDataSource getPool() {
        return pool;
     }


    private void initSource() {
        try (InputStream input = new FileInputStream("src/test/resources/db.properties")) {
           pool = new BasicDataSource();
            Properties cfg = new Properties();
            cfg.load(input);
            Class.forName(cfg.getProperty("jdbc.driver"));
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
}
