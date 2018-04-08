package hibernate.shop.servlets;

import hibernate.shop.User;
import hibernate.shop.UserRepository;
import hibernate.shop.cart.Cart;
import hibernate.shop.cart.CartDetail;
import hibernate.shop.cart.CartRepository;
import hibernate.shop.product.Product;
import hibernate.shop.product.ProductRepository;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;

public class AddProductToCartServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        Long userId = 1L;

        Long productId = parseStringToLong(req.getParameter("productId"));
        BigDecimal productAmount = parseStringToBigDecimal(req.getParameter("productAmount"));

        Optional<Cart> byUserId = CartRepository.findByUserId(userId);
        if (byUserId.isPresent()) {
            Cart cart = byUserId.get();
            Optional<CartDetail> productInCart = cart.getCartDetailSet().stream().
                    filter(cartDetail -> cartDetail.getProduct().getId().equals(productId)).findFirst();
            if (productInCart.isPresent()) {
                //product is in cart
                productInCart.get().setAmount(productInCart.get().getAmount().add(productAmount));
                CartRepository.saveCart(cart);
            } else {
                createNewCartDetail(productId, productAmount, cart);
                CartRepository.saveCart(cart);
            }
        } else {
            Optional<User> byEmail = UserRepository.findByEmail("kowalski@gmail.com");
            Cart cart = new Cart();
            cart.setUser(byEmail.get());
            cart.setCartDetailSet(new HashSet<>());
            createNewCartDetail(productId, productAmount, cart);
            CartRepository.saveCart(cart);
        }
    }

    private Long parseStringToLong(String productId) {
        try {
            return Long.valueOf(productId);
        } catch (NumberFormatException nbf){
            return 0L;
        }
    }

    private BigDecimal parseStringToBigDecimal(String productAmount) {
        try {
            return new BigDecimal(productAmount);
        } catch (NumberFormatException nbf){
            return BigDecimal.ZERO;
        }
    }

    private void createNewCartDetail(Long productId, BigDecimal productAmount, Cart cart) {
        CartDetail cartDetail = new CartDetail();
        cartDetail.setAmount(productAmount);
        Optional<Product> oneById = ProductRepository.findOneById(productId);
        cartDetail.setProduct(oneById.get());
        cartDetail.setPrice(oneById.get().getPrice());
        cart.addCartDetail(cartDetail);
    }
}
