package movies;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class ActorRepository {

    public EntityManagerFactory factory;

    public ActorRepository(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public Actor saveActor(Actor actor) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(actor);
        em.getTransaction().commit();
        em.close();
        return actor;
    }
}
