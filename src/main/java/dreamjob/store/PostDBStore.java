package dreamjob.store;

import dreamjob.model.Post;
import dreamjob.service.CityService;
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
    private static final String SELECTALL = "SELECT * FROM post";
    private static final String SELECTBYID = "SELECT * FROM post WHERE id = ?";
    private static final String INSERT = "INSERT INTO post(name, description, created, visible, city_id) "
            + "VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE post SET name = ?, description = ?, created = ?, city = ? WHERE id = ?";

    public PostDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SELECTALL)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(it.getInt("id"), it.getString("name")));
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
            ps.setDate(3, new Date(new java.util.Date().getTime()));
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
            ps.setTimestamp(3, Timestamp.valueOf(post.getCreated()));
            ps.setInt(4, post.getCity().getId());
            ps.setInt(5, post.getId());
            ps.execute();
        } catch (Exception e) {
            LOG.error("Exception: ", e);
        }
    }

    public Post findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SELECTBYID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return addNewPost(it.getInt("id"),
                            it.getString("name"),
                            it.getString("description"),
                            it.getObject("created", LocalDateTime.class),
                            it.getInt("city_id"));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception: ", e);
        }
        return null;
    }

    private Post addNewPost(int id, String name, String decsription, LocalDateTime created, int cityId) {
        return new Post(id, name, decsription, created, new CityService().findById(cityId));
    }
}
