package com.shop.servlets;

import com.shop.ProjectHelper;
import com.shop.UserSessionHelper;
import com.shop.domain.Product;
import com.shop.domain.ProductRating;
import com.shop.domain.User;
import com.shop.repository.IRepository;
import com.shop.repository.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@WebServlet(name = "AddNewProductRatingServlet", urlPatterns = "/addNewProductRating")
public class AddNewProductRatingServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User userFromCookie = UserSessionHelper.getUserFromCookie(req.getCookies());
        String description = req.getParameter("description");
        Optional<Product> product = ProductRepository.findOneById(ProjectHelper.parseStringToLong(req.getParameter("productId")));
        double rating = ProjectHelper.parseStringToDouble(req.getParameter("rating"));

        if (product.isPresent() && userFromCookie != null && rating > 0) {
            ProductRating productRating = new ProductRating();
            productRating.setCreateDate(LocalDateTime.now());
            productRating.setDescription(description);
            productRating.setRating(rating);
            productRating.setUser(userFromCookie);
            productRating.setProduct(product.get());
            IRepository.save(productRating);
        }
        req.getRequestDispatcher("/product.jsp?productId=" + product.get().getId()).forward(req, resp);
    }
}
