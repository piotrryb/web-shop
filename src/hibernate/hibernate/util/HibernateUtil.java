package hibernate.hibernate.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;
 
    static {
        try {
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError("Connection error with the database!");
        }
    }
 
    public static Session openSession() throws Exception {
    	Session session;
    	try {
    		session = sessionFactory.openSession();
    	} catch (Exception e) {
    		throw new Exception("Connection error with the database!");
    	}
        return session;
    }
}