package dreamjob.store;

import dreamjob.model.City;
import dreamjob.model.Post;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostDBStore {

    private final BasicDataSource pool;
    private static final Logger LOG = LoggerFactory.getLogger(PostDBStore.class.getName());
    private static final String SELECT_ALL = "SELECT * FROM post";
    private static final String SELECT_BY_ID = "SELECT * FROM post WHERE id = ?";
    private static final String INSERT = "INSERT INTO post(name, description, created, visible, city_id) "
            + "VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE post SET name = ?, description = ?, created = ?, city_id = ? WHERE id = ?";

    public PostDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(SELECT_ALL)) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(addNewPost(it));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception: ", e);
        }
        return posts;
    }

    public Post add(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(INSERT,
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.setBoolean(4, post.isVisible());
            ps.setInt(5, post.getCity().getId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception: ", e);
        }
        return post;
    }

    public void update(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(UPDATE)) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(4, post.getCity().getId());
            ps.setInt(5, post.getId());
            ps.execute();
        } catch (Exception e) {
            LOG.error("Exception: ", e);
        }
    }

    public Post findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SELECT_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return addNewPost(it);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception: ", e);
        }
        return null;
    }

    private Post addNewPost(ResultSet resultSet) throws SQLException {
        return new Post(resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getTimestamp("created").toLocalDateTime(),
                new City(resultSet.getInt("city_id"), ""));
    }
}
