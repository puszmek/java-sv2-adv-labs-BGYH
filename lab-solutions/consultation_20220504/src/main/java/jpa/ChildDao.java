package jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class ChildDao {

    private EntityManagerFactory factory;

    public ChildDao(EntityManagerFactory factory) {
        this.factory = factory;
    }

    // gyerekek, akik egy megadott év után születtek
    public List<Child> findChildrenWithBirthAfterGivenYear(int year) {
        EntityManager manager = factory.createEntityManager();
        try {
            return manager
                    .createQuery("select c from Child c where c.yearOfBirth > :year", Child.class)
                    .setParameter("year", year)
                    .getResultList();
        } finally {
            manager.close();
        }
    }

    // gyerek, akinek a szülője a megadott nevű, és a megadott évben született (a gyerek)
    public Child findChildWithYearOfBirthAndWithParent(String name, int year) {
        EntityManager manager = factory.createEntityManager();
        try {
            return manager
//                    .createQuery("select c from Child c join fetch c.person where c.person.name = :name and c.yearOfBirth = :year", Child.class)
                    .createQuery("select c from Child c where c.person.name = :name and c.yearOfBirth = :year", Child.class)
                    .setParameter("name", name)
                    .setParameter("year", year)
                    .getSingleResult();
        } finally {
            manager.close();
        }
    }

    // gyerekek, akik a legtöbben vannak testvérek
    public List<Child> findChildrenWithMostSiblings() {
        EntityManager manager = factory.createEntityManager();
        try {
            return manager
//                    .createQuery("select c from Child c where c.person = (select p from Person p where size(p.children) = (select max(size(p.children)) from Person p))", Child.class)
//                    .createQuery("select c from Child c where c.person.children.size = (select max(size(p.children)) from Person p)", Child.class)
                    .createQuery("select c from Child c where c.person.children.size = (select max(p.children.size) from Person p)", Child.class)
                    .getResultList();
        } finally {
            manager.close();
        }
    }
}
