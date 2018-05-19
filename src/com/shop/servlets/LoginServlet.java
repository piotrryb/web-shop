package com.shop.servlets;

import com.shop.domain.User;
import com.shop.repository.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class LoginServlet extends HttpServlet {
    //  HttpServletRequest contains data from the client (browsers)
    //  e.g. parameters, data from the form, cookies,
    //  HttpServletResponse contains data that will be sent to the client (browsers)
    //  e.g. send HTML code, cookies, redirect
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        System.out.println("password: " + password + " email: " + email);

        Optional<User> byEmailAndPassword = UserRepository.findByEmailAndPassword(email, password);

        byEmailAndPassword.ifPresent(x -> System.out.println("User id: " + x.getId()));

        if (byEmailAndPassword.isPresent()) {
            resp.addCookie(new Cookie("email", byEmailAndPassword.get().getEmail()));
        }
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
