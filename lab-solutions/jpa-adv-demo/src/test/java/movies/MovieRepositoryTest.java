package movies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MovieRepositoryTest {

    MovieRepository movieRepository;
    ActorRepository actorRepository;
    EntityManagerFactory emf;

    @BeforeEach
    void init() {
        emf = Persistence.createEntityManagerFactory("pu");
        movieRepository = new MovieRepository(emf);
        actorRepository = new ActorRepository(emf);
    }

    @Test
    void testSaveMovie() {
        Movie movie = movieRepository.saveMovie(new Movie("Titanic", LocalDate.of(1994, 12, 1), 121));

        assertThat(movie.getId()).isNotEqualTo(null);
    }

    @Test
    void testFindByTitle() {
        Movie movie = movieRepository.saveMovie(new Movie("Titanic", LocalDate.of(1994, 12, 1), 121));
        Optional<Movie> result = movieRepository.findByTitle("Titanic");

        assertThat(result.get().getLength()).isEqualTo(121);
    }

    @Test
    void testFindByTitleWithRatings() {
        Movie movie = new Movie("Titanic", LocalDate.of(1994, 12, 1), 121);
        movie.addRating(new Rating(6.7, "user1"));
        movie.addRating(new Rating(6.9, "user2"));
        movieRepository.saveMovie(movie);

        Movie result = movieRepository.findMovieByTitleWithRatings("Titanic");

        assertThat(result.getLength()).isEqualTo(121);
        assertThat(result.getRatings()).extracting(Rating::getValue).containsExactly(6.7, 6.9);
    }

    @Test
    void testFindMoviesReleasedAfter() {
        Movie movie = new Movie("Titanic", LocalDate.of(1994, 12, 1), 121);
        Movie movie1 = new Movie("LOTR", LocalDate.of(1994, 12, 1), 121);
        movie.addRating(new Rating(6.7, "user1"));
        movie.addRating(new Rating(6.9, "user2"));
        movieRepository.saveMovie(movie);
        movieRepository.saveMovie(movie1);

        List<Movie> result = movieRepository.findMoviesReleasedAfter(LocalDate.of(1994, 11, 11));

        assertThat(result).hasSize(2);
    }

    @Test
    void testSaveMovieWithActors() {
        Movie movie = new Movie("Titanic", LocalDate.of(1997, 12, 19), 194);
        Movie anotherMovie = new Movie("Titan", LocalDate.of(1994, 12, 17), 194);
        Actor firstActor = new Actor("Leonardo DiCaprio", 47);
        Actor secondActor = new Actor("Kate Winslet", 46);
        actorRepository.saveActor(firstActor);
        actorRepository.saveActor(secondActor);
        movie.addActor(firstActor);
        movie.addActor(secondActor);
        anotherMovie.addActor(firstActor);
        movieRepository.saveMovie(movie);
        movieRepository.saveMovie(anotherMovie);

        List<Movie> result = movieRepository.findMoviesReleasedAfter(LocalDate.of(1997, 12, 16));

        assertThat(result).hasSize(1);
    }
}