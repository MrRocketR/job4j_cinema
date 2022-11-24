package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Ticket;

import java.util.*;

@Service
public class BuyService {

    private final TicketService ticketService;
    private final Map<Integer, Ticket> boughtTicketsOnSession = new HashMap<>();

    private final List<Integer> rowsList;

    public BuyService(TicketService ticketService, List<Integer> rowsList) {
        this.ticketService = ticketService;
        this.rowsList = rowsList;
        for (int i = 1; i <= 8; i++) {
            rowsList.add(i);
        }
    }


    public Optional<Ticket> buyTicket(Ticket ticket) {
        Optional<Ticket> boughtTicket = ticketService.addTicket(ticket);
        return boughtTicket;
    }


    public Collection<Integer> filterForFreePlaces(Map<Integer, Ticket> mapOfBought) {
        Collection<Integer> freePlaces = new ArrayList<>();
        for (int seat = 1; seat <= 10; seat++) {
            if (mapOfBought.containsKey(seat)) {
                seat++;
            } else {
                freePlaces.add(seat);
            }
        }
        return freePlaces;
    }

    private Collection<Ticket> getSoldTicketsToFilm(int filmId) {
        Collection<Ticket> soldTickets = ticketService.getSoldTicketsByFilm(filmId);
        return soldTickets;
    }

    public Map<Integer, Ticket> geMapOfSoldTicketsForRow(int filmId, int rowId) {
        Collection<Ticket> tickets = getSoldTicketsToFilm(filmId);
        int row = rowId;
        for (Ticket t : tickets) {
            if (row == t.getRowId()) {
                boughtTicketsOnSession.put(t.getSeatId(), t);
            }
        }
        return boughtTicketsOnSession;
    }

    public List<Integer> getRowsList() {
        return rowsList;
    }
}

