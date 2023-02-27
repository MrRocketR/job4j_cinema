package ru.job4j.cinema.repository;

import lombok.Data;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Genre;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Data
public class FilmStore implements RepoAllNameId<Film> {
    private static final Logger LOG = LogManager.getLogger(FilmStore.class.getName());

    private static final String FIND_BY_ID = "Select * from films where id = ?";
    private static final String FIND_BY_NAME = "Select * from films where name = ?";
    private static final String FIND_ALL = "Select * from films";

    private final BasicDataSource pool;

    @Override
    public List<Film> findAll() {
        List<Film> list = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_ALL)
        ) {
            ResultSet it = ps.executeQuery();
            while (it.next()) {
                Film film = setNewObject(it);
                list.add(film);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return list;
    }

    @Override
    public Film findById(int id) {
        Film film = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    film = setNewObject(it);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return film;
    }

    @Override
    public Film findByName(String name) {
        Film film = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_NAME)
        ) {
            ps.setString(1, name);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    film = setNewObject(it);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return film;
    }


    public Film setNewObject(ResultSet rs) throws SQLException, IOException {
        Film film = new Film();
        film.setId(rs.getInt("id"));
        film.setName(rs.getString("name"));
        film.setDescription(rs.getString("description"));
        film.setYear(rs.getInt("year"));
        film.setGenreId(rs.getInt("genre_id"));
        film.setAge(rs.getInt("minimal_age"));
        film.setDuration(rs.getInt("duration_in_minutes"));
        film.setFileId(rs.getInt("file_id"));
        return film;
    }
}
