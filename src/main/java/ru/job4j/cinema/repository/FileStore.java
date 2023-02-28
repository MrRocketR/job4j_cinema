package ru.job4j.cinema.repository;

import lombok.Data;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.File;

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
public class FileStore implements RepoAllNameId<File> {

    private static final Logger LOG = LogManager.getLogger(FileStore.class.getName());

    private static final String FIND_BY_ID = "Select * from files where id = ?";
    private static final String FIND_BY_NAME = "Select * from files where name = ?";
    private static final String FIND_ALL = "SELECT * FROM files";

    private final BasicDataSource pool;


    private File setNewObject(ResultSet rs) throws SQLException, IOException {
        File file = new File();
        file.setId(rs.getInt("id"));
        file.setName(rs.getString("name"));
        file.setPath(rs.getString("path"));
        file.setImg(getBytes(file.getPath()));
        return file;
    }

    private byte[] getBytes(String path) throws IOException {
        return Files.readAllBytes(Paths.get(path));
    }

    @Override
    public List<File> findAll() {
        List<File> list = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_ALL)
        ) {
            ResultSet it = ps.executeQuery();
            while (it.next()) {
                File file = setNewObject(it);
                list.add(file);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return list;
    }

    @Override
    public File findById(int id) {
        File file = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    file = setNewObject(it);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return file;
    }

    @Override
    public File findByName(String name) {
        File file = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_NAME)
        ) {
            ps.setString(1, name);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    file = setNewObject(it);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return file;
    }

}
