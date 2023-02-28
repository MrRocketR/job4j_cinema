package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;

import java.sql.SQLException;
import java.util.Optional;


public class TicketAndUserStoreTest {
    private static BasicDataSource pool = new DBpropertiesInit().getPool();

    @Test
    public void whenCreateUser() {
        UserStore userStore = new UserStore(pool);
        User user = new User(1, "tester", "tester", "tester");
        Optional<User> actual = userStore.add(user);
        Optional<User> byEmailAndPassword = userStore
                .findUserByEmailAndPassword("tester", "tester");
        Optional<User> byId = userStore.findById(user.getId());
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertTrue(byEmailAndPassword.isPresent());
        Assertions.assertTrue(byId.isPresent());
    }


    @Test
    public void whenBookedTicket() {
        UserStore userStore = new UserStore(pool);
        TicketStore ticketStore = new TicketStore(pool);
        User user = new User(1, "booked", "booked", "booked");
        userStore.add(user);
        Ticket ticket = new Ticket(1, 1,
                1, 1, user.getId());
        Optional<Ticket> booked = ticketStore.add(ticket);
        Assertions.assertTrue(booked.isPresent());
    }

    public void whenFailedToCreateUser() {
        UserStore userStore = new UserStore(pool);
        User user = new User(1, "tester", "tester", "tester");
        Optional<User> actual = userStore.add(user);
        Assertions.assertTrue(actual.isEmpty());
    }


    public void whenAlreadyBookedTicket() {
        UserStore userStore = new UserStore(pool);
        TicketStore ticketStore = new TicketStore(pool);
        User user = new User(1, "booked_already",
                "booked_already", "booked_already");
        userStore.add(user);
        Ticket ticket = new Ticket(1, 1,
                1, 1, user.getId());
        Optional<Ticket> booked = ticketStore.add(ticket);
        Assertions.assertTrue(booked.isEmpty());
    }


    @AfterAll
    public static void close() throws SQLException {
        pool.close();
    }
}