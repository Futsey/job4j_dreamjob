package dreamjob.controller;

import dreamjob.model.Post;
import dreamjob.service.CityService;
import dreamjob.service.PostService;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

import static dreamjob.util.HttpSessionUtil.setGuest;

@ThreadSafe
@Controller
public class PostController {

    private final PostService postService;
    private final CityService cityService;

    public PostController(PostService postService, CityService cityService) {
        this.postService = postService;
        this.cityService = cityService;
    }

    @GetMapping("/posts")
    public String posts(Model model, HttpSession session) {
        model.addAttribute("posts", postService.findAll());
        model.addAttribute("cities", cityService.getAllCities());
        setGuest(model, session);
        return "posts";
    }

    @GetMapping("/formAddPost")
    public String addPost(Model model, HttpSession session) {
        model.addAttribute("post",
                new Post(0, "Введите наименование вакансии", "Введите описание вакансии", LocalDateTime.now()));
        model.addAttribute("cities", cityService.getAllCities());
        setGuest(model, session);
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
     * @param post
     * @return
     */
    @PostMapping("/createPost")
    public String createPost(@ModelAttribute Post post, HttpSession session) {
        post.setCity(cityService.findById(post.getCity().getId()));
        postService.add(post);
        return "redirect:/posts";
    }

    @GetMapping("/formUpdatePost/{postId}")
    public String formUpdatePost(Model model, @PathVariable("postId") int id, HttpSession session) {
        model.addAttribute("post", postService.findById(id));
        model.addAttribute("cities", cityService.getAllCities());
        setGuest(model, session);
        return "updatePost";
    }

    @PostMapping("/updatePost")
    public String updatePost(@ModelAttribute Post post) {
        post.setCity(cityService.findById(post.getCity().getId()));
        postService.update(post);
        return "redirect:/posts";
    }
}
