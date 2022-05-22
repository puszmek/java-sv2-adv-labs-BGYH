package movies;

import java.time.LocalDate;
import java.util.Optional;

public class MovieService {

    private MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie saveMovie(String title, LocalDate releaseDate, int length) {
        if (!checkDate(releaseDate)) {
            throw new IllegalArgumentException("Date is not correct: " + releaseDate);
        }
        return movieRepository.saveMovie(new Movie(title, releaseDate, length));
    }

    public Movie findByTitle(String title) {
        Optional<Movie> result = movieRepository.findByTitle(title);
        if (result.isEmpty()) {
            throw new IllegalArgumentException("Cannot find Movie!");
        }
        return result.get();
    }

    private boolean checkDate(LocalDate releaseDate) {
        return releaseDate.isAfter(LocalDate.of(1911, 1, 1));
    }
}
