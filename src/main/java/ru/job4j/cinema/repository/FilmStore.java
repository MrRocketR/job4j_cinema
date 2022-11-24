package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Film;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FilmStore {
    private static final String ADD = "INSERT INTO films(name, description, poster) VALUES(?, ?, ?)";
    private static final String FIND = "SELECT * FROM films";
    private static final String UPDATE = "UPDATE films SET name = ?, description = ?, poster = ?"
            + "where id = ?";
    private static final String FIND_BY_ID = "SELECT * FROM films WHERE id = ?";
    private static final Logger LOG = LogManager.getLogger(FilmStore.class.getName());
    private final BasicDataSource pool;

    public FilmStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Film> findAll() {
        List<Film> tickets = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND)
        ) {
            ResultSet it = ps.executeQuery();
            while (it.next()) {
                Film film = getFilm(it);
                tickets.add(film);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return tickets;
    }

    public Film add(Film film) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(ADD,
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, film.getName());
            ps.setString(2, film.getDescription());
            ps.setBytes(3, film.getPoster());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    film.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return film;
    }

    public void update(Film film) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(UPDATE)) {
            ps.setString(1, film.getName());
            ps.setString(2, film.getDescription());
            ps.setBytes(3, film.getPoster());
            ps.setInt(4, film.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public Film findById(int id) {
        Film film = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_ID);
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    film = getFilm(it);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return film;
    }


    private Film getFilm(ResultSet it) throws SQLException {
        return new Film(
                it.getInt("id"),
                it.getString("name"),
                it.getString("description"),
                it.getBytes("poster"));
    }
}
