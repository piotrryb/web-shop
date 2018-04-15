package hibernate.shop.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class LogoutServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getCookies() != null) {
            Optional<Cookie> emailCookie = Arrays.stream(req.getCookies()).filter(x -> x.getName().equals("email")).findFirst();
            if (emailCookie.isPresent()) {
                Cookie cookie = emailCookie.get();
                cookie.setMaxAge(1);
                resp.addCookie(cookie);
            }
        }
        req.getRequestDispatcher("/index.jsp?isSuccessLogout=true").forward(req, resp);
    }
}
