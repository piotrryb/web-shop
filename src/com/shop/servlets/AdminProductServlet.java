package com.shop.servlets;

import com.shop.Price;
import com.shop.ProjectHelper;
import com.shop.domain.Category;
import com.shop.domain.Product;
import com.shop.repository.IRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;

@WebServlet(name = "AdminProductServlet", urlPatterns = "/editOrAddProduct")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class AdminProductServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String name = req.getParameter("name");
        Long id = ProjectHelper.parseStringToLong(req.getParameter("id"));
        String description = req.getParameter("description");
        String productType = req.getParameter("productType");
        BigDecimal netPrice = ProjectHelper.parseStringToBigDecimal(req.getParameter("netPrice"));
        BigDecimal grossPrice = ProjectHelper.parseStringToBigDecimal(req.getParameter("grossPrice"));

        Product product = new Product();
        if (id > 0) {
            product.setId(id);
        }
        product.setDate(LocalDate.now());
        product.setDescription(description);
        product.setName(name);

        Price price = new Price();
        price.setNetPrice(netPrice);
        price.setGrossPrice(grossPrice);
        product.setPrice(price);

        Category category = new Category();
        category.setType(productType);
        product.setCategory(category);

        InputStream input = req.getPart("image").getInputStream();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[10240];
        for (int length; (length = input.read(buffer)) > 0; )
            output.write(buffer, 0, length);

        product.setImage(output.toByteArray());
        IRepository.save(product);
    }
}
