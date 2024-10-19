package personal.bookshelf.core.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * A utility class used to provide thread safe Entity Manager
 * as well as other JPA functionalities.
 *
 * @author Michail E. Koutrakis
 */
public class JPAHelper {
    private static EntityManagerFactory emf;
    private static ThreadLocal<EntityManager> threadLocalEntityManager = new ThreadLocal<>();

    private JPAHelper() {}

    public static EntityManagerFactory getEntityMangerFactory() {
        if (emf == null || !emf.isOpen()) {
            emf = Persistence.createEntityManagerFactory("bookshelfPU");
        }
        return emf;
    }

    public static EntityManager getEntityManager() {
        EntityManager em = threadLocalEntityManager.get();
        if (em == null || !em.isOpen()) {
            em = getEntityMangerFactory().createEntityManager();
            threadLocalEntityManager.set(em);
        }
        return em;
    }

    public static void closeEntityManager() {
        getEntityManager().close();
    }

    public static void beginTransaction() {
        getEntityManager().getTransaction().begin();
    }

    public static void commitTransaction() {
        getEntityManager().getTransaction().commit();
    }

    public static void rollbackTransaction() {
        getEntityManager().getTransaction().rollback();
    }

    public static void closeEntityManagerFactory() {
        emf.close();
    }
}
