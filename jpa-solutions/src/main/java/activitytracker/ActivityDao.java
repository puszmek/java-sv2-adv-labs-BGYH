package activitytracker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.List;

public class ActivityDao {

    private EntityManagerFactory factory;

    public ActivityDao(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public void saveActivity(Activity activity) {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(activity);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<Activity> listActivities() {
        EntityManager em = factory.createEntityManager();
        try {
            return em.createQuery("select a from Activity a order by a", Activity.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Activity findActivityById(long id) {
        EntityManager em = factory.createEntityManager();
        try {
            return em.find(Activity.class, id);
        } finally {
            em.close();
        }
    }

    public void deleteActivity(long id) {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            Activity activity = em.getReference(Activity.class, id);
            em.remove(activity);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void updateActivity(long id, String description) {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            Activity activity = em.find(Activity.class, id);
            activity.setDescription(description);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Activity findActivityByIdWithLabels(long id) {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            Activity activity = em.createQuery("select a from Activity a join fetch a.labels where id = :id", Activity.class)
                    .setParameter("id", id)
                    .getSingleResult();
            em.getTransaction().commit();
            return activity;
        } finally {
            em.close();
        }
    }

    public Activity findActivityByIdWithTrackPoints(long id) {
        EntityManager em = factory.createEntityManager();
        try {
            return em.createQuery("select a from Activity a join fetch a.trackPoints where a.id = :id", Activity.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    public List<Coordinate> findTrackPointCoordinatesByDate(LocalDateTime afterThis, int start, int max) {
        EntityManager em = factory.createEntityManager();
        try {
            List<Coordinate> coordinates;
            return coordinates = em.createNamedQuery("trackPointCoordinatesAfterDate")
                    .setParameter("time", afterThis)
                    .setFirstResult(start)
                    .setMaxResults(max)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Object[]> findTrackPointCountByActivity() {
        EntityManager em = factory.createEntityManager();
        try {
            List<Object[]> result = em.createQuery("select a.description, size(a.trackPoints) from Activity a order by a.description", Object[].class)
                    .getResultList();
            return result;
        } finally {
            em.close();
        }
    }
}
