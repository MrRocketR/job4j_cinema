package ru.job4j.cinema.filter;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.User;

@Component
public class AuthFilter implements Filter {

    private final Set<String> filter = new HashSet<>(Arrays.asList("loginPage",
            "login", "regUser", "registration",
            "index", "/logoFile/"));

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        if (findUri(uri)) {
            chain.doFilter(req, res);
            return;
        }
        if (req.getSession().getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "/loginPage");
            return;
        }
        chain.doFilter(req, res);
    }

    private boolean findUri(String uri) {
        return filter.stream().anyMatch(uri::endsWith);

    }
}
