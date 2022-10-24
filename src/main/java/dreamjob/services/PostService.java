package dreamjob.services;

import dreamjob.model.Post;
import dreamjob.store.PostStore;

import java.util.Collection;

public class PostService {

    private static final PostService INST = new PostService();
    private final PostStore postStore = PostStore.instOf();

    private PostService() {
    }

    public static PostService instOf() {
        return INST;
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
