package dreamjob.filter;

import org.springframework.stereotype.Component;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class AuthFilter implements Filter {

    private static final Set<String> TEMPLATES_SET = Set.of("index", "login", "candidates", "posts", "formAddCandidate",
            "formAddPost", "formAddUser", "updateCandidate", "updateUser", "registrationSuccess", "registrationFail");

    private boolean checkSet(HashSet<String> templateSet, String uri) {
        return templateSet.stream().anyMatch(uri::endsWith);
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        if (checkSet((HashSet<String>) TEMPLATES_SET, uri)) {
            chain.doFilter(req, res);
            return;
        }
        if (req.getSession().getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "/loginPage");
            return;
        }
        chain.doFilter(req, res);
    }
}
