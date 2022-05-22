package humans;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;

public class HumanMain {

    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");
        EntityManager em1 = factory.createEntityManager();
        EntityManager em2 = factory.createEntityManager();

        Human human = new Human("John", 12);

        em1.getTransaction().begin();
        em1.persist(human);
        em1.getTransaction().commit();
        em1.close();

//        Human foundHuman = em1.find(Human.class, human.getId());    // PC1-ben van
//
//        em2.getTransaction().begin();
//        em2.remove(foundHuman);             // PC2-ben van, ezért nem törlődik ki
//        em1.getTransaction().commit();

        em2.getTransaction().begin();
        Human foundHuman = em2.find(Human.class, human.getId());    // az egész entitást betölti
        Human anotherFoundHuman = em2.getReference(Human.class, human.getId());     // csak referenciát tölt be az objektumra
        System.out.println(foundHuman);
        System.out.println(anotherFoundHuman.getId());
        em2.lock(foundHuman, LockModeType.PESSIMISTIC_WRITE);
        em2.remove(foundHuman);
        em2.getTransaction().commit();
        em2.close();
    }
}
