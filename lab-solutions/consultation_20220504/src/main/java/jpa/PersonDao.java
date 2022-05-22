package jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class PersonDao {

    private EntityManagerFactory factory;

    public PersonDao(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public void savePerson(Person person) {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // felnőttek, akiknek több gyerekük is van
    public List<Person> findPeopleWithMoreChildren() {
        EntityManager manager = factory.createEntityManager();
        try {
            return manager
//                    .createQuery("select p from Person p where p.children.size > 1 order by p.name", Person.class)
                    .createQuery("select p from Person p where size(p.children) > 1 order by p.name", Person.class)
                    .getResultList();
        } finally {
            manager.close();
        }
    }

    // felnőtt, akinek a legtöbb gyereke van
    public Person findPersonWithMostChildren() {
        EntityManager manager = factory.createEntityManager();
        try {
            return manager
                    .createQuery("select p from Person p where p.children.size = (select max(p.children.size) from Person p)", Person.class)
                    .getSingleResult();
        } finally {
            manager.close();
        }
    }

    // szülő, akihez a paraméterül megadott nevű gyerek tartozik
    public Person findPersonWithNameOfChild(String name) {
        EntityManager manager = factory.createEntityManager();
        try {
            return manager
                    .createQuery("select c.person from Child c where c.name = :name", Person.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } finally {
            manager.close();
        }
    }

    // átlagos gyerekszám
    public Double findAverageNumberOfChild() {
        EntityManager manager = factory.createEntityManager();
        try {
            return manager
                    .createQuery("select avg(p.children.size) from Person p", Double.class)
                    .getSingleResult();
        } finally {
            manager.close();
        }
    }
}
