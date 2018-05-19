package com.shop.servlets;

import com.shop.ProjectHelper;
import com.shop.UserSessionHelper;
import com.shop.domain.Cart;
import com.shop.domain.CartDetail;
import com.shop.domain.Product;
import com.shop.domain.User;
import com.shop.repository.CartRepository;
import com.shop.repository.IRepository;
import com.shop.repository.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;

public class AddProductToCartServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        PrintWriter writer = resp.getWriter();
        User user = UserSessionHelper.getUserFromCookie(req.getCookies());


        Long productId = ProjectHelper.parseStringToLong(req.getParameter("productId"));
        BigDecimal productAmount = ProjectHelper.parseStringToBigDecimal(req.getParameter("productAmount"));

        if (productId > 0 && productAmount.compareTo(BigDecimal.ZERO) > 0 && user != null) {
            Optional<Cart> byUserId = CartRepository.findByUserId(user.getId());

            if (byUserId.isPresent()) {
                Cart cart = byUserId.get();
                Optional<CartDetail> productInCart = cart.getCartDetailSet().stream().
                        filter(cartDetail -> cartDetail.getProduct().getId().equals(productId)).findFirst();

                if (productInCart.isPresent()) {
                    //product is in cart
                    productInCart.get().setAmount(productInCart.get().getAmount().add(productAmount));
                    IRepository.save(cart);
                    writer.write("Increased the number of product with id " + productId + " in cart.");
                } else {
                    // product is not in cart
                    boolean result = createNewCartDetail(productId, productAmount, cart);
                    if (result) {
                        IRepository.save(cart);
                        writer.write("Added product with id " + productId + " to cart.");
                    } else {
                        writer.write("There is no such product.");
                    }
                }
            } else {
                Cart cart = new Cart();
                cart.setUser(user);
                cart.setCartDetailSet(new HashSet<>());
                boolean result = createNewCartDetail(productId, productAmount, cart);
                if (result) {
                    IRepository.save(cart);
                    writer.write("Added product with id " + productId + " to cart.");
                } else {
                    writer.write("There is no such product.");
                }
            }
        } else {
            if (productId <= 0) {
                writer.write("There is no such product.");
            }
            if (productAmount.compareTo(BigDecimal.ZERO) <= 0) {
                writer.write("You can not add product with amount zero or less.");
            }
            if (user == null) {
                writer.write("Login to your account.");
            }
        }
        req.getRequestDispatcher("/cart.jsp").forward(req, resp);
    }

    private boolean createNewCartDetail(Long productId, BigDecimal productAmount, Cart cart) {
        Optional<Product> oneById = ProductRepository.findOneById(productId);

        if (oneById.isPresent()) {
            CartDetail cartDetail = new CartDetail();
            cartDetail.setAmount(productAmount);
            cartDetail.setProduct(oneById.get());
            cartDetail.setPrice(oneById.get().getPrice());
            cart.addCartDetail(cartDetail);
            return true;
        }
        return false;
    }
}
