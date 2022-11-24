package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Ticket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TicketStore {
    private static final String ADD = "INSERT INTO TICKETS (row_id, seat, user_id, film_id) VALUES (?,?,?,?)";

    private static final String FIND = "SELECT * FROM TICKETS";

    private static final String FIND_BY_ID = "SELECT * FROM ticket where id= ?";
    private static final String FIND_BY_FILM = "SELECT * FROM tickets WHERE film_id = ?";

    private static final Logger LOG = LogManager.getLogger(Ticket.class.getName());
    private final BasicDataSource pool;


    public TicketStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<Ticket> add(Ticket ticket) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(ADD, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ticket.getRowId());
            ps.setInt(2, ticket.getSeatId());
            ps.setInt(3, ticket.getUserId());
            ps.setInt(4, ticket.getFilmId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    ticket.setId(id.getInt(1));
                    return Optional.of(ticket);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception", e);
        }
        return Optional.empty();
    }

    public List<Ticket> findAll() {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND)
        ) {
            ResultSet it = ps.executeQuery();
            while (it.next()) {
                Ticket ticket = getTicket(it);
                tickets.add(ticket);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return tickets;
    }

    public List<Ticket> findByFilm(int id) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_FILM);
        ) {
            ps.setInt(1, id);
            ResultSet it = ps.executeQuery();
            while (it.next()) {
                Ticket ticket = getTicket(it);
                tickets.add(ticket);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return tickets;
    }



    public Ticket findById(int id) {
        Ticket ticket = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_ID);
        ) {
            ps.setInt(1, id);
            ResultSet it = ps.executeQuery();
           if (it.next()) {
                ticket = getTicket(it);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return ticket;
    }

    private Ticket getTicket(ResultSet it) throws SQLException {
        return new Ticket(
                it.getInt("id"),
                it.getInt("row_id"),
                it.getInt("seat"),
                it.getInt("user_id"),
                it.getInt("film_id")
        );
    }

}

