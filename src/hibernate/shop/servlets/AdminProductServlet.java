package hibernate.shop.servlets;

import hibernate.shop.Price;
import hibernate.shop.ProjectHelper;
import hibernate.shop.product.Product;
import hibernate.shop.product.ProductRepository;
import hibernate.shop.product.ProductType;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.LocalDate;

@WebServlet(name = "AdminProductServlet", urlPatterns = "/editOrAddProduct")
public class AdminProductServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
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
        product.setProductType(ProductType.valueOf(productType));
        ProductRepository.saveProduct(product);
    }
}
