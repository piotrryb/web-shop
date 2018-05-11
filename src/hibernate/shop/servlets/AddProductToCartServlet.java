package hibernate.shop.servlets;

import hibernate.shop.ProjectHelper;
import hibernate.shop.domain.User;
import hibernate.shop.UserSessionHelper;
import hibernate.shop.domain.Cart;
import hibernate.shop.domain.CartDetail;
import hibernate.shop.repository.CartRepository;
import hibernate.shop.domain.Product;
import hibernate.shop.repository.ProductRepository;

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
                    CartRepository.saveCart(cart);
                    writer.write("Zwiększono liczbę produktu z id " + productId + " w koszyku.");
                } else {
                    // product is not in cart
                    boolean result = createNewCartDetail(productId, productAmount, cart);
                    if (result) {
                        CartRepository.saveCart(cart);
                        writer.write("Dodano produkt z id " + productId + " do koszyka.");
                    } else {
                        writer.write("Nie ma takiego produktu.");
                    }
                }
            } else {
                Cart cart = new Cart();
                cart.setUser(user);
                cart.setCartDetailSet(new HashSet<>());
                boolean result = createNewCartDetail(productId, productAmount, cart);
                if (result) {
                    CartRepository.saveCart(cart);
                    writer.write("Dodano produkt z id " + productId + " do koszyka.");
                } else {
                    writer.write("Nie ma takiego produktu.");
                }
            }
        } else {
            if (productId <= 0) {
                writer.write("Nie ma takiego produktu.");
            }
            if (productAmount.compareTo(BigDecimal.ZERO) <= 0) {
                writer.write("Nie można dodać produktu z ilością mniejszą lub równą zero.");
            }
            if (user == null) {
                writer.write("Proszę się zalogować.");
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
