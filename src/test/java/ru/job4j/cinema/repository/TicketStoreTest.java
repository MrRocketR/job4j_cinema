package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.model.Ticket;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;



public class TicketStoreTest {
    private static BasicDataSource pool;

    @BeforeAll
    public static void initSource() {
        try (InputStream input = new FileInputStream("src\\test\\resources\\db.properties")) {
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

    @Ignore
    @Test
    public void whenTicketAdded() {
        TicketStore store = new TicketStore(pool);
        Ticket ticket = new Ticket(0, 1, 5, 2, 1);
        store.add(ticket);
        Ticket stored = store.findById(ticket.getId());
        Assertions.assertEquals(ticket.getRowId(), stored.getRowId());

    }

}