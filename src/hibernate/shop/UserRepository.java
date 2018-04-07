package hibernate.shop;

import hibernate.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Optional;

public class UserRepository {

    public static Optional<User> findByEmailAndPassword(String email, String password) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            String jpql = "SELECT u FROM User u WHERE u.email = :email AND u.password = :password";
            Query query = session.createQuery(jpql);
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
}
