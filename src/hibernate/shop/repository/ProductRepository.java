package hibernate.shop.repository;

import hibernate.hibernate.util.HibernateUtil;
import hibernate.shop.domain.ProductRating;
import hibernate.shop.domain.Product;
import hibernate.shop.ProductType;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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

    public static List<ProductRating> findAllRatingByProductId(Long id) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            String jpql = "SELECT p FROM ProductRating p WHERE p.product.id = :productRating";
            Query query = session.createQuery(jpql);
            query.setParameter("productRating", id);
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

    public static Double avgRatingByProductId(Long id) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            String jpql = "SELECT avg(p.rating) FROM ProductRating p WHERE p.product.id = :productRating";
            Query query = session.createQuery(jpql);
            query.setParameter("productRating", id);
            return (Double) query.getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0d;
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

    public static List<Product> findAllByNameLikeWithCriteria(String name) {
        Session session = null;
        try {
            // open session
            session = HibernateUtil.openSession();
            // create CriteriaBuilder
            CriteriaBuilder cb = session.getCriteriaBuilder();

            CriteriaQuery<Product> query = cb.createQuery(Product.class);
            // Definiujemy z której encji pobieramy -> from
            Root<Product> from = query.from(Product.class);
            query.select(from);

            //predykaty, czyli warunki where
            Predicate whereNameLike = cb.like(from.get("name"), "%" + name.toUpperCase() + "%");

            CriteriaQuery<Product> criteriaQuery = query.where(whereNameLike);
            return session.createQuery(criteriaQuery).getResultList();
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

    public static List<Product> findAllToyOrCarCheaperThan(BigDecimal price) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            String jpql = "SELECT p FROM Product p WHERE (p.productType = :toy or p.productType = :car) and p.price.grossPrice < :price";
            Query query = session.createQuery(jpql);
            query.setParameter("price", price);
            query.setParameter("toy", ProductType.TOY);
            query.setParameter("car", ProductType.CAR);

            query.setMaxResults(30);
            query.setFirstResult(0);

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

    public static List<Product> findAllToyOrCarCheaperThanLikePredicate(String name) {
        Session session = null;
        try {
            session = HibernateUtil.openSession();
            // create CriteriaBuilder
            CriteriaBuilder cb = session.getCriteriaBuilder();

            CriteriaQuery<Product> query = cb.createQuery(Product.class);
            // Definiujemy z której encji pobieramy -> FROM
            Root<Product> from = query.from(Product.class);
            query.select(from);

            //predykaty, czyli warunki where
            Predicate toyProduct = cb.equal(from.get("productType"), ProductType.TOY);
            Predicate carProduct = cb.equal(from.get("productType"), ProductType.CAR);

            Predicate namePredicate = cb.like(
                    from.get("name"), "%" + name.toUpperCase() + "%");

            Predicate toyOrCar = cb.or(toyProduct, carProduct);
            Predicate whereToyOrCarAndPrice = cb.and(toyOrCar, namePredicate);

            CriteriaQuery<Product> criteriaQuery = query.where(whereToyOrCarAndPrice);
            return session.createQuery(criteriaQuery).getResultList();
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
