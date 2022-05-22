package movies;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    MovieRepository repository;

    @InjectMocks
    MovieService service;

    @Test
    void testSaveMovie() {
        when(repository.saveMovie(any(Movie.class))).thenReturn(
                new Movie(1L, "Titanic", LocalDate.of(1997, 12, 19), 194)
        );
        Movie movie = service.saveMovie("Titanic", LocalDate.of(1997, 12, 19), 194);
        assertThat(movie.getId()).isEqualTo(1L);
        verify(repository).saveMovie(argThat(m -> m.getTitle().equals("Titanic")));
        verify(repository).saveMovie(argThat(m -> m.getReleaseDate().equals(LocalDate.of(1997, 12, 19))));
        verify(repository).saveMovie(argThat(m -> m.getLength() == 194));
    }

    @Test
    void testSaveMovieWithWrongDate() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> service.saveMovie("Titanic", LocalDate.of(1910, 12, 31), 134))
                .withMessage("Date is not correct: 1910-12-31");
        verify(repository, never()).saveMovie(any());

//        assertThatThrownBy(() -> service.saveMovie("Titanic", LocalDate.of(1910, 12, 31), 134))
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessage("Date is not correct: 1910-12-31");

//        assertThatIllegalArgumentException()
//                .isThrownBy(() -> service.saveMovie("Titanic", LocalDate.of(1910, 12, 31), 134))
//                .withMessage("Date is not correct: 1910-12-31");
    }

    @Test
    void testFindByTitle() {
        when(repository.findByTitle("Titanic")).thenReturn(
                Optional.of(new Movie(1L, "Titanic", LocalDate.of(1997, 12, 19), 194))
        );
        Movie movie = service.findByTitle("Titanic");
        assertThat(movie.getTitle().equals("Titanic"));
        verify(repository).findByTitle(any());
    }
}