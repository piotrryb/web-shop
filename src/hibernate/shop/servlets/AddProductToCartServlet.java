package hibernate.shop.servlets;

import hibernate.shop.User;
import hibernate.shop.UserRepository;
import hibernate.shop.cart.Cart;
import hibernate.shop.cart.CartDetail;
import hibernate.shop.cart.CartRepository;
import hibernate.shop.product.Product;
import hibernate.shop.product.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

public class AddProductToCartServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = 1L;
        String productId = req.getParameter("productId");
        String productAmount = req.getParameter("productAmount");

        Optional<Cart> byUserId = CartRepository.findByUserId(userId);
        if (byUserId.isPresent()) {

        }else {
            Optional<User> byEmail = UserRepository.findByEmail("kowalski@gmail.com");
            Cart cart = new Cart();
            cart.setUser(byEmail.get());
            CartDetail cartDetail = new CartDetail();
            cartDetail.setAmount(new BigDecimal(productAmount));
            Optional<Product> oneById = ProductRepository.findOneById(Long.valueOf(productId));
            cartDetail.setProduct(oneById.get());
            cartDetail.setPrice(oneById.get().getPrice());
            cart.addCartDetail(cartDetail);

            CartRepository.saveCart(cart);
        }
    }
}
