package com.shop.servlets;

import com.shop.ProjectHelper;
import com.shop.UserSessionHelper;
import com.shop.domain.Cart;
import com.shop.domain.CartDetail;
import com.shop.domain.User;
import com.shop.repository.CartRepository;
import com.shop.repository.IRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

@WebServlet(name = "DecreaseProductAmountServlet", urlPatterns = "/decreaseProductAmount")
public class DecreaseProductAmountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long productId = ProjectHelper.parseStringToLong(req.getParameter("productId"));
        String method = req.getParameter("method");
        User user = UserSessionHelper.getUserFromCookie(req.getCookies());

        if (user != null) {
            Optional<Cart> cartByUserId = CartRepository.findByUserId(user.getId());

            if (cartByUserId.isPresent()) {
                Optional<CartDetail> cartDetail = cartByUserId.get().getCartDetailSet().stream()
                        .filter(cd -> cd.getProduct().getId().equals(productId)).findFirst();

                if (cartDetail.isPresent()) {
                    CartDetail cd = cartDetail.get();

                    if (method.equals("add")) {
                        cd.setAmount(cd.getAmount().add(BigDecimal.ONE));
                    } else if (method.equals("subtract")) {
                        if (cd.getAmount().compareTo(BigDecimal.ONE) < 1) {
                            // delete product
                            cartByUserId.get().getCartDetailSet().remove(cd);
                        } else {
                            cd.setAmount(cd.getAmount().subtract(BigDecimal.ONE));
                        }
                    }
                    IRepository.save(cartByUserId.get());
                }
            }
        }
        req.getRequestDispatcher("/cart.jsp").forward(req, resp);
    }
}
