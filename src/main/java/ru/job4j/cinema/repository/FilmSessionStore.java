package ru.job4j.cinema.repository;

import lombok.Data;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.FilmSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Data
@Repository
public class FilmSessionStore  {
    private static final Logger LOG = LogManager.getLogger(FilmSessionStore.class.getName());

    private static final String FIND_BY_ID = "Select * from film_sessions where id = ?";
    private static final String FIND_ALL = "Select * from film_sessions";

    private static final String FIND_FILMS_BY_ID = "Select * from film_sessions where film_id = ?";
    private final BasicDataSource pool;


    private FilmSession setNewObject(ResultSet rs) throws SQLException, IOException {
        FilmSession filmSession = new FilmSession();
        filmSession.setId(rs.getInt("id"));
        filmSession.setFilmId(rs.getInt("film_id"));
        filmSession.setHallId(rs.getInt("halls_id"));
        filmSession.setStart(rs.getTimestamp("start_time"));
        filmSession.setEnd(rs.getTimestamp("end_time"));
        filmSession.setPrice(rs.getInt("price"));
        return filmSession;
    }


    public List<FilmSession> findAll() {
        List<FilmSession> list = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_ALL)
        ) {
            ResultSet it = ps.executeQuery();
            while (it.next()) {
                FilmSession filmSession = setNewObject(it);
                list.add(filmSession);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return list;
    }


    public List<FilmSession> findByFilmId(int filmID) {
        List<FilmSession> list = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_FILMS_BY_ID)
        ) {
            ps.setInt(1, filmID);
            ResultSet it = ps.executeQuery();
            while (it.next()) {
                FilmSession filmSession = setNewObject(it);
                list.add(filmSession);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return list;
    }


    public FilmSession findById(int id) {
        FilmSession filmSession = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    filmSession = setNewObject(it);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return filmSession;
    }

}