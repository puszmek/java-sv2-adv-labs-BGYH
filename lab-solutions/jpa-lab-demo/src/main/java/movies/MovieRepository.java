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
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(movie);
        em.getTransaction().commit();
        em.close();
        return movie;
    }

    public Optional<Movie> findByTitle(String title) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Movie result = em.createQuery("select m from Movie m where m.title = :title", Movie.class)
                .setParameter("title", title)
                .getSingleResult();
        em.getTransaction().commit();
        em.close();
        return Optional.of(result);
    }

    public Movie findMovieByTitleWithRatings(String title) {
        EntityManager em = factory.createEntityManager();
        Movie result = em.createQuery("select m from Movie m join fetch m.ratings where m.title=:title", Movie.class)
                .setParameter("title", title)
                .getSingleResult();
        em.close();
        return result;
    }

    public List<Movie> findMoviesReleasedAfter(LocalDate date) {
        EntityManager em = factory.createEntityManager();
        List<Movie> result = em.createQuery("select distinct m from Movie m left join fetch m.ratings where m.releaseDate >: date", Movie.class)
                .setParameter("date", date)         // join - csak azokat az elemeket adja vissza, amikhez tartozik rating
                .getResultList();                      // left join - rating nélkülieket is
        em.close();
        return result;
    }
}
