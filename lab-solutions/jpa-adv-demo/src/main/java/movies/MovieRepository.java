package movies;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class MovieRepository {

    private EntityManagerFactory factory;

    public MovieRepository(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public Movie saveMovie(Movie movie) {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(movie);
        entityManager.getTransaction().commit();
        entityManager.close();
        return movie;
    }

    public Optional<Movie> findByTitle(String title) {
        EntityManager entityManager = factory.createEntityManager();
        Movie result = entityManager.createQuery("select m from Movie m where m.title=:title", Movie.class)
                .setParameter("title", title)
                .getSingleResult();
        entityManager.close();
        return Optional.of(result);
    }

    public Movie findMovieByTitleWithRatings(String title){
        EntityManager entityManager = factory.createEntityManager();
        Movie result = entityManager.createQuery("select m from Movie m join fetch m.ratings where m.title=:title", Movie.class)
                .setParameter("title", title)
                .getSingleResult();
        entityManager.close();
        return result;
    }

    public List<Movie> findMoviesReleasedAfter(LocalDate date){
        EntityManager entityManager = factory.createEntityManager();
        List<Movie> result = entityManager.createQuery("select distinct m from Movie m left join fetch m.ratings where m.releaseDate>:date", Movie.class)
                .setParameter("date", date)
                .getResultList();
        entityManager.close();
        return result;
    }
}
