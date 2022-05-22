package movies;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class RatingRepository {

    private EntityManagerFactory factory;

    public RatingRepository(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public Rating saveRating(Rating rating) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(rating);
        em.getTransaction().commit();
        em.close();
        return rating;
    }

    public Rating findById(Long id) {
        EntityManager em = factory.createEntityManager();
        Rating result = em.find(Rating.class, id);
        em.close();
        return result;
    }
}
