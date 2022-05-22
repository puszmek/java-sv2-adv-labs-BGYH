package jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class BirdDao {

    private EntityManagerFactory factory;

    public BirdDao(EntityManagerFactory factory) {
        this.factory = factory;
    }

    // elment egy madarat az adatbázisba
    public void saveBird(Bird bird) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(bird);
        em.getTransaction().commit();
        em.close();
    }

    // kilistázza az adatbázisból az összes madarat
    public List<Bird> listBirds() {
        EntityManager em = factory.createEntityManager();
        List<Bird> birds = em.createQuery("select b from Bird b", Bird.class)
                .getResultList();
        em.close();
        return birds;
    }

    // kilistázza az adatbázisból a paraméterként megadott fajtájú madarakat
    public List<Bird> listBirdsSpeciesGiven(BirdSpecies species) {
        EntityManager em = factory.createEntityManager();
        List<Bird> birds = em.createQuery("select b from Bird b where b.species = :species", Bird.class)
                .setParameter("species", species)
                .getResultList();
        em.close();
        return birds;
    }

    // kilistázza az adatbázisból azokat a madarakat,
    // melyeknek fészkében a paraméterként megadott számú tojás található
    public List<Bird> listBirdsWithEggsGiven(int eggs) {
        EntityManager em = factory.createEntityManager();
        List<Bird> birds = em.createQuery("select b from Bird b where b.nest.numberOfEggs = :numberOfEggs", Bird.class)
                .setParameter("numberOfEggs", eggs)
                .getResultList();
        em.close();
        return birds;
    }

    // töröl egy madarat az adatbázisból
    public void deleteBird(long id) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
//        Bird bird = em.find(Bird.class, id);
//        em.remove(bird);
        em.createQuery("delete Bird b where b.id = :id")
                .setParameter("id", id)
                .executeUpdate();
        em.getTransaction().commit();
        em.close();
    }
}
