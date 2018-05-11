package com.shop.servlets;

import com.shop.ProjectHelper;
import com.shop.domain.Product;
import com.shop.repository.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = "/productImage", name = "ProductImageServlet")
public class ProductImageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long productId = ProjectHelper.parseStringToLong(req.getParameter("productId"));
        Optional<Product> oneById = ProductRepository.findOneById(productId);
        if (oneById.isPresent()) {
            resp.setHeader("Content-Length", String.valueOf(oneById.get().getImage().length));
            resp.setHeader("Content-Disposition", "inline; filename=\"" + oneById.get().getName() + "\"");
            //Write image data to response
            resp.getOutputStream().write(oneById.get().getImage());
        }

    }
}
