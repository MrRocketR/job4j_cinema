package ru.job4j.cinema.repository;

import lombok.Data;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Hall;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Repository
public class HallStore implements RepoAllNameId<Hall> {
    private static final Logger LOG = LogManager.getLogger(HallStore.class.getName());

    private static final String FIND_BY_ID = "Select * from halls where id = ?";
    private static final String FIND_BY_NAME = "Select * from halls where name = ?";
    private static final String FIND_ALL = "Select * from halls";


    private final BasicDataSource pool;

    @Override
    public List<Hall> getAll() {
        List<Hall> list = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_ALL)
        ) {
            ResultSet it = ps.executeQuery();
            while (it.next()) {
                Hall hall = setNewObject(it);
                list.add(hall);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return list;
    }

    @Override
    public Hall getById(int id) {
        Hall hall = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    hall = setNewObject(it);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return hall;
    }

    @Override
    public Hall getByName(String name) {
        Hall hall = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_NAME)
        ) {
            ps.setString(1, name);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    hall = setNewObject(it);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return hall;
    }

    public Hall setNewObject(ResultSet rs) throws SQLException {
        Hall hall = new Hall();
        hall.setId(rs.getInt("id"));
        hall.setName(rs.getString("name"));
        hall.setRows(rs.getInt("row_count"));
        hall.setPlaces(rs.getInt("place_count"));
        hall.setDescription(rs.getString("description"));
        hall.setFreePlaces(setFreePlaces(hall.getRows(), hall.getPlaces()));
        return hall;
    }

    /**
    пока под вопросов необходимость этого метода
     */
    public Map<Integer, List<Integer>> setFreePlaces(int rows, int places) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 1; i <= rows; i++) {
            List<Integer> r_places = new ArrayList<>();
            for (int j = 1; j <= places; j++) {
                r_places.add(j);
            }
            map.put(i, r_places);
        }
       return map;
    }
}
