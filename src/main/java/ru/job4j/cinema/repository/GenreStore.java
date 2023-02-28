package ru.job4j.cinema.repository;

import lombok.Data;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Genre;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Data
@Repository
public class GenreStore implements RepoAllNameId<Genre> {

    private static final Logger LOG = LogManager.getLogger(GenreStore.class.getName());

    private static final String FIND_BY_ID = "Select * from genres where id = ?";
    private static final String FIND_BY_NAME = "Select * from genres where name = ?";
    private static final String FIND_ALL = "Select * from genres";

    private final BasicDataSource pool;

    @Override
    public List<Genre> findAll() {
        List<Genre> list = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_ALL)
        ) {
            ResultSet it = ps.executeQuery();
            while (it.next()) {
                Genre genre = setNewObject(it);
                list.add(genre);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return list;
    }

    @Override
    public Genre findById(int id) {
        Genre genre = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    genre = setNewObject(it);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return genre;
    }

    @Override
    public Genre findByName(String name) {
        Genre genre = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_NAME)
        ) {
            ps.setString(1, name);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    genre = setNewObject(it);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return genre;
    }


    public Genre setNewObject(ResultSet rs) throws SQLException, IOException {
        return new Genre(rs.getInt("id"),
                rs.getString("name"));
    }
}
