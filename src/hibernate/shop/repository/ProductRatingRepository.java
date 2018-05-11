package hibernate.shop.repository;

import hibernate.hibernate.util.HibernateUtil;
import hibernate.shop.domain.ProductRating;
import org.hibernate.Session;

public class ProductRatingRepository {

    public static void saveProductRating(ProductRating productRating) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            session.getTransaction().begin();
            session.saveOrUpdate(productRating);
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
}
