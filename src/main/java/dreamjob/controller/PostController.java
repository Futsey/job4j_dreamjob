package dreamjob.controller;

import dreamjob.model.Candidate;
import dreamjob.model.Post;
import dreamjob.store.PostStore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
public class PostController {

    private final PostStore postStore = PostStore.instOf();

    @GetMapping("/posts")
    public String posts(Model model) {
        model.addAttribute("posts", postStore.findAll());
        return "posts";
    }

    @GetMapping("/formAddPost")
    public String addPost(Model model) {
        model.addAttribute("post",
                new Post(0, "Заполните название", "Заполните описание", LocalDateTime.now()));
        return "addPost";
    }

    @GetMapping("/formAddCandidate")
    public String addCandidate(Model model) {
        model.addAttribute("candidate",
                new Candidate(0, "Введите имя кандидата", "Заполните описание", LocalDateTime.now()));
        return "addCandidate";
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
     * TODO: реализовать генерацию id (по согласованию с ментором)
     * @param post
     * @return
     */
    @PostMapping("/createPost")
    public String createPost(@ModelAttribute Post post) {
        postStore.add(post);
        return "redirect:/posts";
    }
}
