package jpa;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChildDaoTest {

    EntityManagerFactory factory;

    ChildDao childDao;

    PersonDao personDao;

    @BeforeEach
    void init() {
        factory = Persistence.createEntityManagerFactory("pu");
        childDao = new ChildDao(factory);
        personDao = new PersonDao(factory);

        Person person = new Person("Józsi", 30);
        Child kati = new Child("Kati", 2017);
        Child zsuzsi = new Child("Zsuzsi", 2022);
        person.addChild(kati);
        person.addChild(zsuzsi);
        personDao.savePerson(person);

        Person person1 = new Person("person1", 39);
        Child child1 = new Child("child1", 2009);
        Child child2 = new Child("child2", 2012);
        Child child3 = new Child("child3", 2015);
        Child child4 = new Child("child4", 2017);
        Child child5 = new Child("child5", 2020);
        person1.addChild(child1);
        person1.addChild(child2);
        person1.addChild(child3);
        person1.addChild(child4);
        person1.addChild(child5);
        personDao.savePerson(person1);

        Person person2 = new Person("person2", 34);
        Child child6 = new Child("child6", 2019);
        person2.addChild(child6);
        personDao.savePerson(person2);

        Person person3 = new Person("person3", 42);
        Child child7 = new Child("child7", 2022);
        Child child8 = new Child("child8", 2018);
        person3.addChild(child7);
        person3.addChild(child8);
        personDao.savePerson(person3);

        Person person4 = new Person("person4", 19);
        personDao.savePerson(person4);

        Person person5 = new Person("person5", 24);
        Child child9 = new Child("child9", 2021);
        person5.addChild(child9);
        personDao.savePerson(person5);
    }

    @AfterEach
    void close() {
        factory.close();
    }

    @Test
    void testFindChildrenWithBirthAfterGivenYear() {
        List<Child> expected = childDao.findChildrenWithBirthAfterGivenYear(2016);

        assertEquals(8, expected.size());
    }

    @Test
    void testFindChildWithYearOfBirthAndWithParent() {
        Child expected = childDao.findChildWithYearOfBirthAndWithParent("person1", 2015);

        assertEquals("child3", expected.getName());
    }

    @Test
    void testFindChildrenWithMostSiblings() {
        List<Child> expected = childDao.findChildrenWithMostSiblings();

        assertEquals(5, expected.size());
    }
}
