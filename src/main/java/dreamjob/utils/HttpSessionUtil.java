package dreamjob.utils;

import dreamjob.model.User;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

public final class HttpSessionUtil {

    private HttpSessionUtil() {
    }

    public static void setGuest(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
    }
}
