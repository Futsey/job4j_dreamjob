package dreamjob.filter;

import org.springframework.stereotype.Component;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class AuthFilter implements Filter {

    String[] templatesArr = {"index", "login", "candidates", "posts", "formAddCandidate",
            "formAddPost", "formAddUser", "updateCandidate", "updateUser", "registrationSuccess", "registrationFail"};


    private Set<String> templatesToSet() {
        return new HashSet<String>(Arrays.asList(templatesArr));
    }

    private boolean checkSet(HashSet<String> templateSet, String uri) {
        return templateSet.stream().anyMatch(setEl -> setEl.endsWith(uri));
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        templatesToSet();
        if (checkSet((HashSet<String>) templatesToSet(), uri)) {
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
