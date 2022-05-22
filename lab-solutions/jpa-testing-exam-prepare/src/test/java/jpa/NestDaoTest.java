package jpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Nest database operations test")
class NestDaoTest {

    BirdDao birdDao;

    NestDao nestDao;

    Nest swallowNest;

    @BeforeEach
    void init() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");
        birdDao = new BirdDao(factory);
        nestDao = new NestDao(factory);

        Nest nestingBox = new NestingBox(1, 20, 25);
        nestDao.saveNest(nestingBox);
        Bird owl1 = new Bird(BirdSpecies.OWL, nestingBox);
        Bird owl2 = new Bird(BirdSpecies.OWL, nestingBox);
        Bird owl3 = new Bird(BirdSpecies.OWL, nestingBox);
        Bird owl4 = new Bird(BirdSpecies.OWL, nestingBox);
        birdDao.saveBird(owl1);
        birdDao.saveBird(owl2);
        birdDao.saveBird(owl3);
        birdDao.saveBird(owl4);

        swallowNest = new SwallowNest(5, 275);
        nestDao.saveNest(swallowNest);
        Bird swallow1 = new Bird(BirdSpecies.SWALLOW, swallowNest);
        Bird swallow2 = new Bird(BirdSpecies.SWALLOW, swallowNest);
        birdDao.saveBird(swallow1);
        birdDao.saveBird(swallow2);

        Nest roundNest1 = new RoundNest(1, 15);
        nestDao.saveNest(roundNest1);
        Bird blackbird1 = new Bird(BirdSpecies.BLACKBIRD, roundNest1);
        Bird blackbird2 = new Bird(BirdSpecies.BLACKBIRD, roundNest1);
        Bird blackbird3 = new Bird(BirdSpecies.BLACKBIRD, roundNest1);
        birdDao.saveBird(blackbird1);
        birdDao.saveBird(blackbird2);
        birdDao.saveBird(blackbird3);

        Nest roundNest2 = new RoundNest(4, 100);
        nestDao.saveNest(roundNest2);
        Bird stork1 = new Bird(BirdSpecies.STORK, roundNest2);
        Bird stork2 = new Bird(BirdSpecies.STORK, roundNest2);
        Bird stork3 = new Bird(BirdSpecies.STORK, roundNest2);
        birdDao.saveBird(stork1);
        birdDao.saveBird(stork2);
        birdDao.saveBird(stork3);
    }

    // az egyszerű mentés után az egyedi azonosító alapján való visszakérést teszteled
    @Test
    @DisplayName("Test finding nest by id")
    void testFindById() {
        assertEquals(5, nestDao.findNestById(swallowNest.getId()).getNumberOfEggs());
    }

    // a findNestWithMinBirds() metódus működésének tesztelésére.
    // Itt a visszakapott fészek madarait tartalmazó listára írj AssertJ segítségével assertet
    @Test
    @DisplayName("Test find the nest with the minimal number of birds")
    void testFindNestWithMinBirds() {
        Nest expected = nestDao.findNestWithMinBirds();

        assertThat(expected.getBirds())
                .hasSize(2)
                .extracting(Bird::getSpecies)
                .containsExactly(BirdSpecies.SWALLOW, BirdSpecies.SWALLOW);
    }

    // legyen egy paraméterezett teszteset, amely egy másik metódusban definiált különböző értékekkel
    // hívja meg a countNestsWithEggsGiven(int eggs) metódust
    @ParameterizedTest(name = "eggs = {0}, result = {1}")   // az átadott paramétereket index alapján helyettesíti be
    @DisplayName("Test number of nests with given number of eggs")
    @MethodSource("getValues")
    // másik metódusból szerezze az értékeket, String-ként kell megadni a metódus (statikus) nevét
    void testCountNestsWithEggsGiven(int eggs, int result) {
        assertEquals(result, nestDao.countNestsWithEggsGiven(eggs));
        // a result helyére a második számot helyettesíti be 2/0/1/1
        // az eggs helyére pedig az első számot 1/2/4/5
    }

    static Stream<Arguments> getValues() {
        return Stream.of(
                Arguments.arguments(1, 2),      // ha 1 tojást adok meg, olyan fészekből 2 van
                Arguments.arguments(2, 0),
                Arguments.arguments(4, 1),
                Arguments.arguments(5, 1)
        );
    }
}
