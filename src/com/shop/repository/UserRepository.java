package com.shop.repository;

import com.hibernate.util.HibernateUtil;
import com.shop.domain.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Optional;

public class UserRepository {

    public static Optional<User> findByEmailAndPassword(String email, String password) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            String queryJPQL = "SELECT u FROM User u WHERE u.email = :email AND u.password = :password";
            Query query = session.createQuery(queryJPQL);
            query.setParameter("email", email);
            query.setParameter("password", password);

            return Optional.ofNullable((User) query.getSingleResult());
        } catch (Exception ex) {
            ex.printStackTrace();
            return Optional.empty();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public static Optional<User> findByEmail(String email) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            String queryJPQL = "SELECT u FROM User u WHERE u.email = :email";
            Query query = session.createQuery(queryJPQL);
            query.setParameter("email", email);

            return Optional.ofNullable((User) query.getSingleResult());
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
