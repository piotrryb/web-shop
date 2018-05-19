package com.shop.repository;

import com.hibernate.util.HibernateUtil;
import com.shop.domain.Order;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class OrderRepository {

    public static Optional<Order> findOrder(Long id) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            String queryJPQL = "SELECT o FROM Order o LEfT JOIN FETCH o.orderDetailSet od WHERE o.id= :id";
            Query query = session.createQuery(queryJPQL);
            query.setParameter("id", id);
            return Optional.ofNullable((Order) query.getSingleResult());
        } catch (Exception ex) {
            ex.printStackTrace();
            return Optional.empty();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public static List<Order> findAll() {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            String queryJPQL = "SELECT o FROM Order o LEFT JOIN FETCH o.orderDetailSet od";
            Query query = session.createQuery(queryJPQL);
            return query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Collections.emptyList();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public static List<Order> findAllByUserId(Long userId, int offset) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            String queryJPQL = "SELECT o FROM Order o WHERE o.user.id = :userId ORDER BY o.id DESC";
            Query query = session.createQuery(queryJPQL);
            query.setParameter("userId", userId);
            query.setMaxResults(15);
            query.setFirstResult(offset);
            return query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Collections.emptyList();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public static List<Order> findAllOrderWithProduct(Long productId) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            String queryJPQL = "SELECT o FROM Order o LEFT JOIN FETCH o.orderDetailSet od WHERE od.product.id = :id";
            Query query = session.createQuery(queryJPQL);
            query.setParameter("id", productId);
            return query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Collections.emptyList();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
