package com.shop.repository;

import com.hibernate.util.HibernateUtil;
import com.shop.domain.Cart;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Optional;

public class CartRepository {

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

    public static Optional<Cart> findByUserId(Long userId) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            String queryJPQL = "SELECT c FROM Cart c WHERE c.user.id = :userId";
            Query query = session.createQuery(queryJPQL);
            query.setParameter("userId", userId);
            return Optional.ofNullable((Cart) query.getSingleResult());
        } catch (Exception ex) {
            ex.printStackTrace();
            return Optional.empty();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public static void removeCart(Cart cart) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            session.getTransaction().begin();
            session.remove(cart);
            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
