package dreamjob.store;

import dreamjob.Main;
import dreamjob.model.City;
import dreamjob.model.Post;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PostDBStoreTest {

    @Test
    public void whenCreatePost() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post = new Post(1,
                "Junior Java Job",
                "Weak Programmer",
                LocalDateTime.now(),
                new City(1, "Москва"));
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName(), is(post.getName()));
    }

    @Test
    public void whenFindPostById() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post = new Post(1,
                "Junior Java Job",
                "Weak Programmer",
                LocalDateTime.now(),
                new City(1, "Москва"));
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getId(), is(post.getId()));
    }

    @Test
    public void whenUpdatePostName() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post = new Post(1,
                "Junior Java Job",
                "Weak Programmer",
                LocalDateTime.now(),
                new City(1, "Москва"));
        store.add(post);
        post.setName("Weak Junior");
        store.update(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName(), is(post.getName()));
    }

    @Test
    public void whenUpdatePostDesc() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post = new Post(1,
                "Junior Java Job",
                "Weak Programmer",
                LocalDateTime.now(),
                new City(1, "Москва"));
        store.add(post);
        post.setDescription("Really Weak Junior");
        store.update(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getDescription(), is(post.getDescription()));
    }

    @Test
    public void whenUpdatePostCreateDate() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post = new Post(1,
                "Junior Java Job",
                "Weak Programmer",
                LocalDateTime.now(),
                new City(1, "Москва"));
        store.add(post);
        post.setCity(new City(2, "СПб"));
        store.update(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getCity(), is(post.getCity()));
    }

    @Test
    public void whenFindAllPosts() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post = new Post(1,
                "Junior Java Job",
                "Weak Programmer",
                LocalDateTime.now(),
                new City(1, "Москва"));
        Post post2 = new Post(2,
                "Middle Java Job",
                "Average Programmer",
                LocalDateTime.now(),
                new City(2, "СПб"));
        store.add(post);
        store.add(post2);
        Post postInDb = store.findById(post.getId());
        Post postInDb2 = store.findById(post2.getId());
        PostDBStore storeDB = new PostDBStore(new Main().loadPool());
        storeDB.add(postInDb);
        storeDB.add(postInDb2);
        assertThat(store.findAll(), is(storeDB.findAll()));
    }
}