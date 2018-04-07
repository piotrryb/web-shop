package hibernate.shop.servlets;

import hibernate.shop.User;
import hibernate.shop.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class LoginServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        System.out.println("password: " + password + " email: " + email);

        Optional<User> byEmailAndPassword = UserRepository.findByEmailAndPassword(email, password);

        byEmailAndPassword.ifPresent(x -> System.out.println("User id: " + x.getId()));
    }
}
