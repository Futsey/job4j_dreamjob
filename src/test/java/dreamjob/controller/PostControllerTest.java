package dreamjob.controller;

import dreamjob.model.City;
import dreamjob.model.Post;
import dreamjob.service.CityService;
import dreamjob.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


class PostControllerTest {

    @Test
    public void whenPosts() {
        List<Post> posts = Arrays.asList(
                new Post(1, "New post"),
                new Post(2, "New post")
        );
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        HttpSession session = mock(HttpSession.class);
        when(postService.findAll()).thenReturn(posts);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.posts(model, session);
        verify(model).addAttribute("posts", posts);
        assertThat(page, is("posts"));
    }

    @Test
    public void whenAddPost() {
        Post input = new Post(1,
                "Junior Java Job",
                "Weak Programmer",
                LocalDateTime.now(),
                new City(1, "Москва"));
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        HttpSession session = mock(HttpSession.class);
        Model model = mock(Model.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.addPost(model, session);
        verify(model).addAttribute("post",
                new Post(0, "Введите наименование вакансии", "Введите описание вакансии", LocalDateTime.now()));
        when(postService.findAll()).thenReturn(
                List.of(new Post(1,
                "Junior Java Job",
                "Weak Programmer",
                LocalDateTime.now(),
                new City(1, "Москва"))));
        postService.add(input);
        verify(postService).add(input);
        assertThat(page, is("addPost"));
        assertThat(postService.findAll(), is(List.of(input)));
    }

    @Test
    public void whenFindByIdPost() {
        Post input = new Post(1,
                "Junior Java Job",
                "Weak Programmer",
                LocalDateTime.now(),
                new City(1, "Москва"));
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        HttpSession session = mock(HttpSession.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.createPost(input, session);
        postService.findById(input.getId());
        verify(postService).findById(1);
        assertThat(page, is("redirect:/posts"));
    }

    @Test
    public void whenUpdatePost() {
        Post input = new Post(1,
                "Junior Java Job",
                "Weak Programmer",
                LocalDateTime.now(),
                new City(1, "Москва"));
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String newName = "C job";
        input.setName(newName);
        String page = postController.updatePost(input);
        verify(postService).update(input);
        assertThat(page, is("redirect:/posts"));
        assertThat(input.getName(), is(newName));
    }

    @Test
    public void whenFindAllPosts() {
        Post input1 = new Post(1,
                "Junior Java Job",
                "Weak Programmer",
                LocalDateTime.now(),
                new City(1, "Москва"));
        Post input2 = new Post(2,
                "Middle Java Job",
                "Average Programmer",
                LocalDateTime.now(),
                new City(2, "СПБ"));
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        HttpSession session = mock(HttpSession.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page1 = postController.createPost(input1, session);
        String page2 = postController.createPost(input2, session);
        when(postService.findAll()).thenReturn(
                List.of(new Post(1,
                                "Junior Java Job",
                                "Weak Programmer",
                                LocalDateTime.now(),
                                new City(1, "Москва")),
                        new Post(2,
                                "Middle Java Job",
                                "Average Programmer",
                                LocalDateTime.now(),
                                new City(2, "СПБ"))));
        verify(postService).add(input1);
        assertThat(page1, is("redirect:/posts"));
        assertThat(page2, is("redirect:/posts"));
        assertThat(postService.findAll(), is(List.of(input1, input2)));
    }
}