package hibernate.shop.order;

import hibernate.hibernate.util.HibernateUtil;
import hibernate.shop.order.Order;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.List;

public class OrderRepository {

    public static void saveOrder(Order order) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            session.getTransaction().begin();
            session.saveOrUpdate(order);
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

    public static List<Order> findAll() {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            String jpql = "SELECT o FROM Order o LEFT JOIN FETCH o.orderDetailSet od";
            Query query = session.createQuery(jpql);
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
            String jpql = "SELECT o FROM Order o LEFT JOIN FETCH o.orderDetailSet od WHERE od.product.id = :id";
            Query query = session.createQuery(jpql);
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
