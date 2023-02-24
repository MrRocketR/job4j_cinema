package ru.job4j.cinema.repository;

import lombok.Data;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.File;
import ru.job4j.cinema.model.Session;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Data
@Repository
public class SessionStore {
    private static final Logger LOG = LogManager.getLogger(FileStore.class.getName());

    private static final String FIND_BY_ID = "Select * from film_sessions where id = ?";
    private static final String FIND_BY_NAME = "Select * from film_sessions where name = ?";
    private static final String FIND_ALL = "Select * from film_sessions";

    private final BasicDataSource pool;


    private Session setNewObject(ResultSet rs) throws SQLException, IOException {
        Session session = new Session();
        session.setFilmId(rs.getInt("film_id"));
        session.setHallId(rs.getInt("halls_id "));
        session.setStart(rs.getTimestamp("start"));
        session.setEnd(rs.getTimestamp("start"));
        session.setId(rs.getInt("price"));
        return session;
    }


    public List<Session> getAll() {
        List<Session> list = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_ALL)
        ) {
            ResultSet it = ps.executeQuery();
            while (it.next()) {
                Session session = setNewObject(it);
                list.add(session);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return list;
    }


    public Session getById(int id) {
        Session session = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    session = setNewObject(it);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return session;
    }

}