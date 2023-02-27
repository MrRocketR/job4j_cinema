package ru.job4j.cinema.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.TicketStore;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Data
@Service
public class TicketService {

    private final TicketStore ticketStore;


    public List<Ticket> findAll() {
        return ticketStore.findAll();
    }

    public Ticket findById(int id) {
        return ticketStore.findById(id);
    }

    public Optional<Ticket> addTicket(Ticket ticket) {
        return ticketStore.add(ticket);
    }

}
