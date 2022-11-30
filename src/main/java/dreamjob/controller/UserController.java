package dreamjob.controller;

import dreamjob.model.User;
import dreamjob.service.UserService;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@ThreadSafe
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/formAddUser")
    public String addUser(Model model) {
        model.addAttribute("user", new User(0, "Введите имя", "Введите пароль"));
        return "addUser";
    }

    @PostMapping("/registration")
    public String registration(Model model, @ModelAttribute User user) {
        Optional<User> regUser = userService.add(user);
        if (regUser.isEmpty()) {
            model.addAttribute("message",
                    "Пользователь с такой почтой уже существует");
            return "redirect:/fail";
        }
        return "redirect:/success";
    }

    @GetMapping("/fail")
    public String fail(Model model) {
        model.addAttribute("fail", "Registration failed");
        return "registrationFailed";
    }

    @GetMapping("/success")
    public String success(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("success", "Registration successful");
        return "registrationSuccess";
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userDb = userService.findUserByEmailAndPassword(
                user.getEmail(), user.getPassword()
        );
        if (userDb.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userDb.get());
        return "redirect:/index";
    }
}
