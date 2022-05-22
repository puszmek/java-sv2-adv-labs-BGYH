package activitytracker;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class ActivityDaoTest {

    EntityManagerFactory factory;

    ActivityDao activityDao;

    @BeforeEach
    void init() {
        factory = Persistence.createEntityManagerFactory("pu");
        activityDao = new ActivityDao(factory);
    }

    @AfterEach
    void close() {
        factory.close();
    }

    @Test
    void testSaveActivity() {
        Activity activity = new Activity(LocalDateTime.of(2022, 4, 5, 6, 20), "futás az erdőben", ActivityType.RUNNING);
        activityDao.saveActivity(activity);

        assertThat(activity.getStartTime()).isEqualTo(LocalDateTime.of(2022, 4, 5, 6, 20));
        assertThat(activity.getDescription()).isEqualTo("futás az erdőben");
        assertThat(activity.getType() == ActivityType.RUNNING);
    }

    @Test
    void testListActivities() {
        Activity firstActivity = new Activity(LocalDateTime.of(2022, 4, 5, 6, 20), "futás az erdőben", ActivityType.RUNNING);
        Activity secondActivity = new Activity(LocalDateTime.of(2022, 4, 10, 16, 30), "kosárlabdázás barátokkal", ActivityType.BASKETBALL);
        Activity thirdActivity = new Activity(LocalDateTime.of(2022, 4, 15, 6, 30), "futás a parkban", ActivityType.RUNNING);
        Activity fourthActivity = new Activity(LocalDateTime.of(2022, 4, 20, 17, 45), "kerékpározás a város körül", ActivityType.BIKING);
        activityDao.saveActivity(firstActivity);
        activityDao.saveActivity(secondActivity);
        activityDao.saveActivity(thirdActivity);
        activityDao.saveActivity(fourthActivity);
        List<Activity> activities = activityDao.listActivities();

        assertThat(activities)
                .hasSize(4)
                .extracting(Activity::getStartTime, Activity::getDescription, Activity::getType)
                .contains(tuple(LocalDateTime.of(2022, 4, 5, 6, 20), "futás az erdőben", ActivityType.RUNNING),
                        tuple(LocalDateTime.of(2022, 4, 10, 16, 30), "kosárlabdázás barátokkal", ActivityType.BASKETBALL),
                        tuple(LocalDateTime.of(2022, 4, 15, 6, 30), "futás a parkban", ActivityType.RUNNING),
                        tuple(LocalDateTime.of(2022, 4, 20, 17, 45), "kerékpározás a város körül", ActivityType.BIKING));
        assertThat(activities)
                .hasSize(4)
                .extracting(Activity::getStartTime)
                .doesNotContain(LocalDateTime.of(2022, 4, 20, 17, 40));
    }

    @Test
    void testFindActivityById() {
        Activity activity = new Activity(LocalDateTime.of(2022, 4, 10, 16, 30), "kosárlabdázás barátokkal", ActivityType.BASKETBALL);
        activityDao.saveActivity(activity);
        long id = activity.getId();
        Activity loadedActivity = activityDao.findActivityById(id);

        assertThat(loadedActivity.getType() == ActivityType.BASKETBALL);
    }

    @Test
    void testDeleteActivity() {
        Activity activity = new Activity(LocalDateTime.of(2022, 4, 5, 6, 20), "futás az erdőben", ActivityType.RUNNING);
        activityDao.saveActivity(activity);
        long id = activity.getId();
        activityDao.deleteActivity(id);
        List<Activity> activities = activityDao.listActivities();

        assertThat(activities)
                .hasSize(0)
                .doesNotContain(activity);
    }

    @Test
    void testUpdateActivity() {
        Activity activity = new Activity(LocalDateTime.of(2022, 4, 20, 17, 45), "kerékpározás a város körül", ActivityType.BIKING);
        activityDao.saveActivity(activity);
        long id = activity.getId();
        activityDao.updateActivity(id, "kerékpározás az erdőben");
        Activity activityWithNewDescription = activityDao.findActivityById(id);

        assertThat(activity.getCreatedAt().isEqual(LocalDateTime.now()));
        assertThat(activity.getUpdatedAt().isEqual(LocalDateTime.now()));
        assertThat(activityWithNewDescription.getDescription()).isEqualTo("kerékpározás az erdőben");
    }

    @Test
    void testFindActivityByIdWithLabels() {
        Activity activity = new Activity(LocalDateTime.of(2022, 4, 5, 6, 20), "futás az erdőben", ActivityType.RUNNING);
        activity.setLabels(Arrays.asList("futás", "erdő", "délután"));
        activityDao.saveActivity(activity);
        Activity activityWithLabels = activityDao.findActivityByIdWithLabels(activity.getId());

        assertThat(activityWithLabels.getLabels())
                .hasSize(3)
                .containsAll(List.of("futás", "erdő", "délután"));
    }

    @Test
    void testFindActivityByIdWithTrackPoints() {
        Activity activity = new Activity(LocalDateTime.of(2022, 4, 5, 6, 20), "futás az erdőben", ActivityType.RUNNING);
        activity.addTrackPoint(new TrackPoint(LocalDate.of(2022, 4, 6), 47.4982, 19.0407));
        activity.addTrackPoint(new TrackPoint(LocalDate.of(2022, 4, 5), 47.4982, 19.0406));
        activityDao.saveActivity(activity);
        Activity activityWithTrackPoints = activityDao.findActivityByIdWithTrackPoints(activity.getId());

        assertThat(activityWithTrackPoints.getTrackPoints())
                .hasSize(2)
                .extracting(TrackPoint::getLatitude, TrackPoint::getLongitude)
                .containsExactly(tuple(47.4982, 19.0406), tuple(47.4982, 19.0407));
    }

    @Test
    void testFindTrackPointCoordinatesByDate() {
        Activity firstActivity = new Activity(LocalDateTime.of(2022, 4, 5, 6, 20), "futás az erdőben", ActivityType.RUNNING);
        firstActivity.addTrackPoint(new TrackPoint(LocalDate.of(2022, 4, 7), 47.4982, 19.0407));
        firstActivity.addTrackPoint(new TrackPoint(LocalDate.of(2022, 4, 5), 47.4982, 19.0406));

        Activity secondActivity = new Activity(LocalDateTime.of(2022, 4, 10, 16, 30), "kosárlabdázás barátokkal", ActivityType.BASKETBALL);
        secondActivity.addTrackPoint(new TrackPoint(LocalDate.of(2022, 4, 10), 47.4921, 19.0417));
        secondActivity.addTrackPoint(new TrackPoint(LocalDate.of(2022, 4, 11), 47.4922, 19.0416));
        secondActivity.addTrackPoint(new TrackPoint(LocalDate.of(2022, 4, 11), 47.4932, 19.0426));
        secondActivity.addTrackPoint(new TrackPoint(LocalDate.of(2022, 4, 12), 47.4832, 19.0326));

        activityDao.saveActivity(firstActivity);
        activityDao.saveActivity(secondActivity);

        List<Coordinate> result = activityDao.findTrackPointCoordinatesByDate(LocalDateTime.of(2022, 4, 5, 6, 30), 1, 3);

        assertThat(result)
                .hasSize(3);
    }

//    @Test
//    void testFindTrackPointCountByActivity() {
//        Activity firstActivity = new Activity(LocalDateTime.of(2022, 4, 5, 6, 20), "futás az erdőben", ActivityType.RUNNING);
//        firstActivity.addTrackPoint(new TrackPoint(LocalDate.of(2022, 4, 5), 46.4922, 18.3407));
//        firstActivity.addTrackPoint(new TrackPoint(LocalDate.of(2022, 4, 5), 45.4482, 17.0406));
//        firstActivity.addTrackPoint(new TrackPoint(LocalDate.of(2022, 4, 6), 47.3982, 19.2406));
//        firstActivity.addTrackPoint(new TrackPoint(LocalDate.of(2022, 4, 7), 46.4981, 19.1406));
//
//        Activity secondActivity = new Activity(LocalDateTime.of(2022, 4, 10, 16, 30), "kosárlabdázás barátokkal", ActivityType.BASKETBALL);
//        secondActivity.addTrackPoint(new TrackPoint(LocalDate.of(2022, 4, 10), 47.4921, 19.0417));
//        secondActivity.addTrackPoint(new TrackPoint(LocalDate.of(2022, 4, 11), 47.4922, 19.0416));
//
//        activityDao.saveActivity(firstActivity);
//        activityDao.saveActivity(secondActivity);
//
//        List<Object[]> result = activityDao.findTrackPointCountByActivity();
//
//        assertThat(result)
//                .hasSize(2);
//    }
}