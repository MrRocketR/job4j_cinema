package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.TicketStore;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private final TicketStore store;

    public TicketService(TicketStore store) {
        this.store = store;
    }

    public Collection<Ticket> getSoldTickets() {
        Collection<Ticket> tickets = store.findAll();
        return tickets;
    }

    public Collection<Ticket> getSoldTicketsByFilm(int id) {
        Collection<Ticket> tickets = store.findByFilm(id);
        return tickets;

    }

    public Ticket findById(int id) {
        return store.findById(id);
    }

    public Optional<Ticket> addTicket(Ticket ticket) {
        return store.add(ticket);
    }


}
