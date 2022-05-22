package activitytracker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class AreaDao {

    private EntityManagerFactory factory;

    public AreaDao(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public void saveArea(Area area) {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(area);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Area findAreaByName(String name) {
        EntityManager em = factory.createEntityManager();
        Area area;
        try {
            area = em.createQuery("select a from Area a join fetch a.activities where a.name = :name", Area.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } finally {
            em.close();
        }
        return area;
    }

    public Area findAreaById(Long id) {
        EntityManager em = factory.createEntityManager();
        Area area;
        try {
            area = em.createQuery("select a from Area a join fetch a.cities where a.id = :id", Area.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } finally {
            em.close();
        }
        return area;
    }
}
