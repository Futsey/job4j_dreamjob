package dreamjob.controller;

import dreamjob.model.Post;
import dreamjob.services.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
public class PostController {

    private final PostService postService = PostService.instOf();

    @GetMapping("/posts")
    public String posts(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "posts";
    }

    @GetMapping("/formAddPost")
    public String addPost(Model model) {
        model.addAttribute("post",
                new Post(0, "Введите наименование вакансии", "Введите описание вакансии", LocalDateTime.now()));
        return "addPost";
    }

     /**
     * Метод для обработки HttpRequest`а мануально с созданием нового экземпляра Post
     * @param страница с вакансиями
     * @return возврат на страницу с вакансиями
    @PostMapping("/createPost")
    public String createPost(HttpServletRequest req) {
        String name = req.getParameter("name");
        System.out.println(name);
        postStore.add(new Post(1, name));
        return "redirect:/posts";
    }
     */

    /**
     * Метод для обработки HttpRequest`а силами Spring с автоматическим созданием нового экземпляра Post
     * TO DO: реализовать генерацию id (по согласованию с ментором)
     * @param post
     * @return
     */
    @PostMapping("/createPost")
    public String createPost(@ModelAttribute Post post) {
        postService.add(post);
        return "redirect:/posts";
    }

    @GetMapping("/formUpdatePost/{postId}")
    public String formUpdatePost(Model model, @PathVariable("postId") int id) {
        model.addAttribute("post", postService.findById(id));
        return "updatePost";
    }

    @PostMapping("/updatePost")
    public String updatePost(@ModelAttribute Post post) {
        postService.update(post);
        return "redirect:/posts";
    }
}
