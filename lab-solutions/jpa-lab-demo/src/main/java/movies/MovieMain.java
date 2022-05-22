package movies;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;

public class MovieMain {

    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");
        EntityManager em = factory.createEntityManager();
        EntityManager em2 = factory.createEntityManager();
        EntityManager em3 = factory.createEntityManager();

        Movie movie = new Movie("Spiderman", LocalDate.of(2021, 12, 17), 120);
        AfterMovieScene afterMovieScene = new AfterMovieScene(1.2, "After cast");

        em.getTransaction().begin();
        em.persist(movie);
        em.persist(afterMovieScene);
        em.getTransaction().commit();
        em.close();

        em2.getTransaction().begin();
//        Movie foundMovie = em2.find(Movie.class, movie.getId());
        Movie foundMovie = em2.getReference(Movie.class, movie.getId());        // ha nem találja exception-t dob (update-nél érdemes használni)
        AfterMovieScene foundScene = em2.find(AfterMovieScene.class, afterMovieScene.getId());      // ha nem találja, null-t ad vissza
        foundScene.setMovie(foundMovie);        // betölt egy entitást, és a memóriába létrehoz belőle egy másolatot
        em2.getTransaction().commit();      // megnézi, hogy történt-e változás azon, amin dolgoztam, ha igen -> update művelet
        em2.close();

//        em3.getTransaction().begin();
//        Movie found = em3.find(Movie.class,movie.getId());
//        found.setTitle("Spiderman");
//        em3.getTransaction().commit();
//        em3.close();
    }
}
