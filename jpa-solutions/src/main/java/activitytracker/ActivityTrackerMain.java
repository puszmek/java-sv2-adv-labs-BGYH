package activitytracker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ActivityTrackerMain {

    public static void main(String[] args) {
        ActivityTrackerMain activityTrackerMain = new ActivityTrackerMain();

        Activity firstActivity = new Activity(LocalDateTime.of(2022, 4, 5, 6, 20), "futás az erdőben", ActivityType.RUNNING);
        Activity secondActivity = new Activity(LocalDateTime.of(2022, 4, 10, 16, 30), "kosárlabdázás barátokkal", ActivityType.BASKETBALL);
        Activity thirdActivity = new Activity(LocalDateTime.of(2022, 4, 15, 6, 30), "futás a parkban", ActivityType.RUNNING);
        Activity fourthActivity = new Activity(LocalDateTime.of(2022, 4, 20, 17, 45), "kerékpározás a város körül", ActivityType.BIKING);

        List<Activity> activities = new ArrayList<>();
        activities.add(firstActivity);
        activities.add(secondActivity);
        activities.add(thirdActivity);
        activities.add(fourthActivity);

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("pu");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            activityTrackerMain.insertActivities(activities, entityManager);
            entityManager.getTransaction().commit();

            // lekérdezés
            List<Activity> resultList = entityManager.createQuery("select a from Activity a order by a.type", Activity.class)
                    .getResultList();
            System.out.println("List of activities order by type: " + resultList);

            // beolvasás azonosító alapján
            long id = fourthActivity.getId();
            Activity activity = entityManager.find(Activity.class, id);
            System.out.println("Activity by id: " + activity);

            // módosítás
            entityManager.getTransaction().begin();
            activity.setDescription("délutáni kerékpározás");
            entityManager.getTransaction().commit();

            resultList = entityManager.createQuery("select a from Activity a order by a.startTime", Activity.class)
                    .getResultList();
            System.out.println("List of activities order by start time with new description: " + resultList);

            // törlés
            entityManager.getTransaction().begin();
            entityManager.remove(activity);
            entityManager.getTransaction().commit();

            resultList = entityManager.createQuery("select a from Activity a order by a.id", Activity.class)
                    .getResultList();
            System.out.println("List of activities order by id: " + resultList);

        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    private void insertActivities(List<Activity> activities, EntityManager entityManager) {
        for (Activity actual : activities) {
            entityManager.persist(actual);
        }
    }
}
