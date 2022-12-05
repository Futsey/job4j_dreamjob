package dreamjob.filter;

import org.springframework.stereotype.Component;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthFilter implements Filter {

    private static final String MAIN_PAIGE = "index";
    private static final String LOGIN = "login";
    private static final String CANDIDATES = "candidates";
    private static final String POSTS = "posts";
    private static final String ADD_CANDIDATE = "formAddCandidate";
    private static final String ADD_POST = "formAddPost";
    private static final String ADD_USER = "formAddUser";
    private static final String UPDATE_CANDIDATE = "updateCandidate";
    private static final String UPDATE_USER = "updateUser";
    private static final String SUCCESS = "registrationSuccess";
    private static final String FAIL = "registrationFail";

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        if (uri.endsWith(MAIN_PAIGE)
                || uri.endsWith(LOGIN)
                || uri.endsWith(CANDIDATES)
                || uri.endsWith(POSTS)
                || uri.endsWith(ADD_CANDIDATE)
                || uri.endsWith(ADD_POST)
                || uri.endsWith(ADD_USER)
                || uri.endsWith(UPDATE_CANDIDATE)
                || uri.endsWith(UPDATE_USER)
                || uri.endsWith(SUCCESS)
                || uri.endsWith(FAIL)) {
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
