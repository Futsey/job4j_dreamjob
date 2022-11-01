package dreamjob.service;

import dreamjob.model.Post;
import dreamjob.store.PostStore;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;

import java.util.Collection;

@ThreadSafe
@Service
public class PostService {

    private final PostStore postStore;

    public PostService(PostStore store) {
        this.postStore = store;
    }

    public Collection<Post> findAll() {
        return postStore.findAll();
    }

    public void add(Post post) {
        postStore.add(post);
    }

    public Post findById(int id) {
        return postStore.findById(id);
    }

    public void update(Post post) {
        postStore.update(post);
    }
}
