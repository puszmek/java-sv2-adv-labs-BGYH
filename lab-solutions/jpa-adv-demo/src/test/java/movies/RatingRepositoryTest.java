package movies;

import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;

class RatingRepositoryTest {

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");

    RatingRepository ratingRepository = new RatingRepository(factory);
    MovieRepository movieRepository = new MovieRepository(factory);

    @Test
    void testSaveAndFindById() {
        Movie movie = new Movie("Titanic", LocalDate.of(1997, 12, 19), 194);
        Rating firstRating = new Rating(3.8, "user1");
        Rating secondRating = new Rating(4.2, "user2");
        movieRepository.saveMovie(movie);
        firstRating.setMovie(movie);
        secondRating.setMovie(movie);
        ratingRepository.saveRating(firstRating);
        ratingRepository.saveRating(secondRating);

        Rating result = ratingRepository.findById(firstRating.getId());

        System.out.println(result.getId());
    }
}