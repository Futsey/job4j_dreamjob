package dreamjob.store;

import dreamjob.model.City;
import dreamjob.model.Post;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class PostStore {

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final AtomicInteger idGen = new AtomicInteger(3);

    private PostStore() {
        posts.put(1, new Post(1, "Junior Java Job", "Weak Programmer", LocalDateTime.now(), new City(4, "Омск")));
        posts.put(2, new Post(2, "Middle Java Job", "Average Programmer", LocalDateTime.now(), new City(5, "Новосибирск")));
        posts.put(3, new Post(3, "Senior Java Job", "Strong Programmer", LocalDateTime.now(), new City(6, "Тюмень")));
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public void add(Post post) {
        post.setId(idGen.incrementAndGet());
        posts.put(post.getId(), post);
    }

    public Post findById(int id) {
        return posts.get(id);
    }

    public void update(Post post) {
        posts.replace(post.getId(), post);
    }
}
