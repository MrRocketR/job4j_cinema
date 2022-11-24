package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.Main;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Ticket;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class TicketStoreTest {
    private static BasicDataSource pool = new Main().loadPool();
    @Ignore
    @AfterClass
    public static void closeCnPool() throws SQLException {
        pool.close();
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