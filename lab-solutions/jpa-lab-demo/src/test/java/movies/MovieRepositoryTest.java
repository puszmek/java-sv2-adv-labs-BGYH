package movies;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MovieRepositoryTest {

    EntityManagerFactory emf;

    MovieRepository repository;

    @BeforeEach
    void init() {
        emf = Persistence.createEntityManagerFactory("pu");
        repository = new MovieRepository(emf);
    }

    @AfterEach
    void close() {
        emf.close();
    }

    @Test
    void testSaveMovie() {
        Movie movie = repository.saveMovie(new Movie("Titanic", LocalDate.of(1997, 12, 19), 194));

        assertThat(movie.getId()).isNotEqualTo(null);
    }

    @Test
    void testFindByTitle() {
        Movie movie = repository.saveMovie(new Movie("Titanic", LocalDate.of(1997, 12, 19), 194));
        Optional<Movie> result = repository.findByTitle("Titanic");

        assertThat(result.get().getLength()).isEqualTo(194);
    }

    @Test
    void testFindMovieByTitleWithRatings() {
        Movie movie = new Movie("Titanic", LocalDate.of(1997, 12, 19), 194);
        movie.addRating(new Rating(7.5, "user1"));
        movie.addRating(new Rating(7.7, "user2"));
        repository.saveMovie(movie);
        Movie result = repository.findMovieByTitleWithRatings("Titanic");

        assertThat(result.getLength()).isEqualTo(194);
        assertThat(result.getRatings())
                .extracting(Rating::getValue)
                .containsExactly(7.5, 7.7);
    }

    @Test
    void testFindMoviesReleasedAfter() {
        Movie movie = new Movie("Titanic", LocalDate.of(1997, 12, 19), 194);
        Movie anotherMovie = new Movie("LOTR", LocalDate.of(1997, 12, 19), 194);
        movie.setDirector(new Director("James Cameron"));
        movie.addRating(new Rating(7.1, "user1"));
        movie.addRating(new Rating(6.9, "user2"));
        repository.saveMovie(movie);
        repository.saveMovie(anotherMovie);
        List<Movie> result = repository.findMoviesReleasedAfter(LocalDate.of(1997, 12, 18));

        System.out.println(result.size());      // 2: distinct-tel egy film egyszer szerepel, left join-nal az is szerepel, amihez nem tartozik Rating
        System.out.println(result.get(0).getRatings());     // [Rating{value=7.1, username='user1'}, Rating{value=6.9, username='user2'}]

        assertThat(result).hasSize(2);
    }
}
