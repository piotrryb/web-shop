package hibernate.shop.servlets;

import hibernate.shop.domain.User;
import hibernate.shop.repository.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String password2 = req.getParameter("password2");

        boolean isValid = true;
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || password2.isEmpty()) {
            System.out.println("Proszę uzupełnić formularz!");
            isValid = false;
        }

        if (!password.equals(password2)) {
            System.out.println("Hasła są różne!");
            isValid = false;
        }

        Optional<User> byEmail = UserRepository.findByEmail(email);
        if (byEmail.isPresent()) {
            isValid = false;
            System.out.println("Użytkownik z takim emailem już istnieje!");
        }

        if (isValid) {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            UserRepository.saveUser(user);
        }
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
