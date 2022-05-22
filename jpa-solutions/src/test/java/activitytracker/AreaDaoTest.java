package activitytracker;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class AreaDaoTest {

    EntityManagerFactory factory;

    AreaDao areaDao;

    ActivityDao activityDao;

    @BeforeEach
    void init() {
        factory = Persistence.createEntityManagerFactory("pu");
        areaDao = new AreaDao(factory);
        activityDao = new ActivityDao(factory);
    }

    @AfterEach
    void close() {
        factory.close();
    }

    @Test
    void testSaveArea() {
        Activity firstActivity = new Activity(LocalDateTime.of(2022, 4, 5, 6, 20), "futás az erdőben", ActivityType.RUNNING);
        Activity secondActivity = new Activity(LocalDateTime.of(2022, 4, 10, 16, 30), "kosárlabdázás barátokkal", ActivityType.BASKETBALL);
        Activity thirdActivity = new Activity(LocalDateTime.of(2022, 4, 15, 6, 30), "futás a parkban", ActivityType.RUNNING);
        Activity fourthActivity = new Activity(LocalDateTime.of(2022, 4, 20, 17, 45), "kerékpározás a város körül", ActivityType.BIKING);

        activityDao.saveActivity(firstActivity);
        activityDao.saveActivity(secondActivity);
        activityDao.saveActivity(thirdActivity);
        activityDao.saveActivity(fourthActivity);

        Area firstArea = new Area("Kiskunság");
        Area secondArea = new Area("Nagykunság");
        Area thirdArea = new Area("Matyóföld");

        firstArea.addActivity(firstActivity);
        firstArea.addActivity(thirdActivity);
        secondArea.addActivity(firstActivity);
        secondArea.addActivity(secondActivity);
        thirdArea.addActivity(fourthActivity);

        areaDao.saveArea(firstArea);
        areaDao.saveArea(secondArea);
        areaDao.saveArea(thirdArea);

        Area area = areaDao.findAreaByName("Nagykunság");

        assertThat(area.getActivities())
                .hasSize(2)
                .extracting(Activity::getType)
                .containsOnly(ActivityType.RUNNING, ActivityType.BASKETBALL);
    }

    @Test
    void saveThenFindArea() {
        Area area = new Area("Nagykunság");

        area.putCity(new City("Karcag", 19732));
        area.putCity(new City("Kisújszállás", 10870));
        area.putCity(new City("Kunhegyes", 7521));

        areaDao.saveArea(area);

        Area anotherArea = areaDao.findAreaById(area.getId());

        assertThat(anotherArea.getCities())
                .hasSize(3);
        assertThat(Set.of("Karcag", "Kisújszállás", "Kunhegyes")).isEqualTo(anotherArea.getCities().keySet());
    }
}