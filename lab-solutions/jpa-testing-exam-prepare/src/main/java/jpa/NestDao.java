package jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class NestDao {

    private EntityManagerFactory factory;

    public NestDao(EntityManagerFactory factory) {
        this.factory = factory;
    }

    // elment egy fészket az adatbázisba
    public void saveNest(Nest nest) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(nest);
        em.getTransaction().commit();
        em.close();
    }

    // az egyedi azonosítója alapján megkeres egy fészket az adatbázisba
    public Nest findNestById(long id) {
        EntityManager em = factory.createEntityManager();
        Nest nest = em.find(Nest.class, id);
        em.close();
        return nest;
    }

    // megkeresi azt a fészket az adatbázisban, amelyben a legkevesebb madár található.
    // Figyelj arra, hogy a visszakapott találatban lekérhető legyen a fészekhez tartozó madarak listája is!
    public Nest findNestWithMinBirds() {
        EntityManager em = factory.createEntityManager();
        Nest nest = em.createQuery("select distinct n from Nest n left join fetch n.birds where n.birds.size = (select min(m.birds.size) from Nest m)", Nest.class)
                .getSingleResult();
        em.close();
        return nest;
    }

    // visszaadja azon fészkek számát, melyben a paraméterként megadott számú tojás található
    public long countNestsWithEggsGiven(int eggs) {
        EntityManager em = factory.createEntityManager();
        long result = em.createQuery("select count(n) from Nest n where n.numberOfEggs = :numberOfEggs", Long.class)
                .setParameter("numberOfEggs", eggs)
                .getSingleResult();
        em.close();
        return result;
    }
}
