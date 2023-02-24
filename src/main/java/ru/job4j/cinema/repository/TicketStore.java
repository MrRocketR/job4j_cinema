package ru.job4j.cinema.repository;

import lombok.Data;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Ticket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Repository
public class TicketStore {
    private static final String ADD = "INSERT INTO TICKETS (row_id, session_id, row_number, place_number, user_id) VALUES (?,?,?,?,?)";

    private static final String FIND_ALL = "SELECT * FROM tickets";

    private static final String FIND_BY_ID = "SELECT * FROM tickets where id= ?";


    private static final Logger LOG = LogManager.getLogger(Ticket.class.getName());
    private final BasicDataSource pool;


    public TicketStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<Ticket> add(Ticket ticket) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(ADD, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ticket.getRow());
            ps.setInt(2, ticket.getSessionId());
            ps.setInt(3, ticket.getRow());
            ps.setInt(4, ticket.getPlace());
            ps.setInt(5, ticket.getUserId());
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
             PreparedStatement ps = cn.prepareStatement(FIND_ALL)
        ) {
            ResultSet it = ps.executeQuery();
            while (it.next()) {
                Ticket ticket = setNewObject(it);
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
                ticket = setNewObject(it);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return ticket;
    }

    private Ticket setNewObject(ResultSet it) throws SQLException {
        return new Ticket(
                it.getInt("id"),
                it.getInt("session_id"),
                it.getInt("row_number"),
                it.getInt("place_number"),
                it.getInt("user_id")
        );
    }

}

