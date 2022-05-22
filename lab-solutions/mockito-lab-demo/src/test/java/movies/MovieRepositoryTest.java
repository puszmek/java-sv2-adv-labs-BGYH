package movies;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class MovieRepositoryTest {

    Flyway flyway;
    MovieRepository repository;

    @BeforeEach
    void init() {
        JdbcDataSource dataSource = new JdbcDataSource();

        dataSource.setURL("jdbc:h2:~/test");
        dataSource.setUser("sa");
        dataSource.setPassword("sa");

        flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.clean();
        flyway.migrate();

        repository = new MovieRepository(dataSource);
    }

    @Test
    void testSaveMovie() {
        Movie result = repository.saveMovie(new Movie("Titanic", LocalDate.of(1997, 12, 19), 194));
        assertThat(result.getId()).isNotEqualTo(null);
        assertThat(result.getTitle()).isEqualTo("Titanic");
    }
}