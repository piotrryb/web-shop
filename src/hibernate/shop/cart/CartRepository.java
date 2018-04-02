package hibernate.shop.cart;

import hibernate.hibernate.util.HibernateUtil;
import hibernate.shop.cart.Cart;
import org.hibernate.Session;

import java.util.Optional;

public class CartRepository {

    public static void saveCart(Cart cart) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            session.getTransaction().begin();
            session.saveOrUpdate(cart);
            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public static Optional<Cart> findOneById(Long id) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            Cart cart = session.find(Cart.class, id);
            return Optional.ofNullable(cart);
        } catch (Exception ex) {
            ex.printStackTrace();
            return Optional.empty();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
