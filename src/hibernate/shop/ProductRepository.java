package hibernate.shop;

import hibernate.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ProductRepository {

    public static void saveProduct(Product product) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            session.getTransaction().begin();
            session.saveOrUpdate(product);
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

    public static void deleteById(Long id) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            session.getTransaction().begin();
            Optional<Product> oneById = findOneById(id);
            if (oneById.isPresent()) {
                session.delete(oneById.get());
            }
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

    public static Optional<Product> findOneById(Long id) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            Product product = session.find(Product.class, id);
            return Optional.ofNullable(product);
        } catch (Exception ex) {
            ex.printStackTrace();
            return Optional.empty();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public static List<Product> findAll() {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            String jpql = "SELECT p FROM Product p";
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

    public static List<Product> findAllByProductType(ProductType productType) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            String jpql = "SELECT p FROM Product p WHERE p.productType = :productType";
            Query query = session.createQuery(jpql);
            query.setParameter("productType", productType);
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

    public static Long countAllByProductType(ProductType productType) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            String jpql = "SELECT COUNT(p.id) FROM Product p WHERE p.productType = :productType";
            Query query = session.createQuery(jpql);
            query.setParameter("productType", productType);
            return (Long) query.getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0L;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public static List<Product> findAllWithPriceLess(BigDecimal priceGross) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            String jpql = "SELECT p FROM Product p WHERE p.price.grossPrice < :price";
            Query query = session.createQuery(jpql);
            query.setParameter("price", priceGross);
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

    public static List<Product> findAllByName(String name) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            String jpql = "SELECT p FROM Product p WHERE UPPER(p.name) like :name";
            Query query = session.createQuery(jpql);
            query.setParameter("name", "%" + name.toUpperCase() + "%");
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

    public static List<Product> findAllNative() {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            String sql = "SELECT * FROM product AS p";
            Query query = session.createNativeQuery(sql, Product.class);
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
