package jpa;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Bird database operations test")
class BirdDaoTest {

    BirdDao birdDao;

    NestDao nestDao;

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

        Nest swallowNest = new SwallowNest(5, 275);
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

    // az egyszer?? ment??s ut??ni list??z??s tesztel??se. Itt AssertJ seg??ts??g??vel ??rd meg az assertet

    @Test
    @DisplayName("Test listing birds")
    void testListBirds() {
        List<Bird> expected = birdDao.listBirds();

        assertThat(expected)
                .hasSize(12)
                .extracting(Bird::getSpecies)
                .contains(BirdSpecies.BLACKBIRD, BirdSpecies.OWL, BirdSpecies.STORK, BirdSpecies.SWALLOW);
    }

    //a listBirdsSpeciesGiven(BirdSpecies species) met??dus m??k??d??s??nek tesztel??se

    @Test
    @DisplayName("Test listing birds with given species")
    void testListBirdsSpeciesGiven() {
        List<Bird> expected = birdDao.listBirdsSpeciesGiven(BirdSpecies.OWL);

        assertEquals(4, expected.size());
    }

    // legyen egy ism??tl??ses teszteset (@RepeatedTest) a listBirdsWithEggsGiven(int eggs) met??dus tesztel??s??re,
    // ahol a teszteset k??l??nb??z?? ??rt??kekkel h??vja meg ezt a met??dust

    @RepeatedTest(value = 3, name = "count: {currentRepetition} from {totalRepetitions}")
    @DisplayName("Test listing birds with given number of eggs")
    void testListBirdsWithEggsGiven(RepetitionInfo info) {
        int[][] values = {{1, 7}, {4, 3}, {5, 2}};      // indexel??se 0-t??l indul
        int i = info.getCurrentRepetition();    // indexel??se 1-t??l indul

        assertEquals(values[i - 1][1], birdDao.listBirdsWithEggsGiven(values[i - 1][0]).size());
        // ki kell vonni az i index??b??l 1-et
        // a m??sodik ??rt??ket (valuest) v??rom el eredm??nyk??nt pl.7/3/2, ha megh??vom a met??dust az els?? elemre 1/4/5
    }

    // legyen egy teszteset, melyben egy adatb??zisbeli rekord t??rl??s??t teszteled

    @Test
    @DisplayName("Test removing bird from database")
    void testDeleteBird() {
        Bird bird = new Bird();
        birdDao.saveBird(bird);

        assertEquals(13, birdDao.listBirds().size());

        birdDao.deleteBird(bird.getId());

        assertEquals(12, birdDao.listBirds().size());
    }
}
