package ru.job4j.cinema.repository;

import lombok.Data;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.User;

import java.sql.*;
import java.util.Optional;

@Repository
@Data
public class UserStore {

    private static final String ADD = "INSERT INTO users(full_name, email, password) VALUES(?, ?, ?)";
    private static final String FIND = "SELECT * FROM users WHERE email = ? AND password = ?";
    private static final Logger LOG = LogManager.getLogger(UserStore.class.getName());
    private final BasicDataSource pool;

    public UserStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<User> add(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(ADD, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                    return Optional.of(user);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception", e);
        }
        return Optional.empty();
    }

    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND)
        ) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    User user = setNewObject(resultSet);
                    return Optional.of(user);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception", e);
        }
        return Optional.empty();
    }

    public User setNewObject(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getInt("id"),
                resultSet.getString("username"),
                resultSet.getString("email"),
                resultSet.getString("password")
        );
    }
}
