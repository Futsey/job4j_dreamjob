package dreamjob.store;

import dreamjob.model.User;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDBStore {

    private final BasicDataSource pool;
    private static final Logger LOG = LoggerFactory.getLogger(UserDBStore.class.getName());
    private static final String SELECT_ALL = "SELECT * FROM users";
    private static final String SELECT_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String INSERT = "INSERT INTO users(email, password, created)"
            + "VALUES (?, ?, ?)";
    private static final String UPDATE = "UPDATE users SET email = ?, password = ? WHERE id = ?";

    public UserDBStore(BasicDataSource pool) {
        this.pool = pool;
    }


    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(SELECT_ALL)) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    users.add(addNewUser(it));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception: ", e);
        }
        return users;
    }

    public Optional<User> add(User user) {
        Optional<User> notNullUser = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(INSERT,
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
            notNullUser = Optional.of(user);
        } catch (Exception e) {
            LOG.error("Exception: ", e);
        }
        return notNullUser;
    }

    public void update(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(UPDATE)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getId());
            ps.execute();
        } catch (Exception e) {
            LOG.error("Exception: ", e);
        }
    }

    public Optional<User> findById(int id) {
        Optional<User> notNullUser = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SELECT_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    notNullUser = Optional.of(addNewUser(it));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception: ", e);
        }
        return notNullUser;
    }

    private User addNewUser(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getInt("id"),
                resultSet.getString("email"),
                resultSet.getString("password"),
                resultSet.getTimestamp("created").toLocalDateTime());
    }
}
