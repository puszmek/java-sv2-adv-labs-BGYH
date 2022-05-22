package jpa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Bird business logic test")
@ExtendWith(MockitoExtension.class)         // ezzel vehetjük igénybe a mockolás "szolgáltatásait"
class BirdServiceTest {

    @Mock                       // mit mockolunk
    BirdDao birdDao;

    @InjectMocks                // hova illesszük be a mockolt objektumot
    BirdService birdService;

    // A BirdServiceTest osztályban a BirdService függőségét a tesztosztály mockolja!
    // Írj egy tesztmetódust a getBirdStatistics() tesztelésére, melyben megadod,
    // hogy a mockolt objektumot használó metódus milyen értékkel térjen vissza, * when()
    // erre írj egy assertet, valamint ellenőrizz rá arra is,                    * assert...()
    // hogy ténylegesen meghívásra került-e a szükséges adatbáziskezelő metódus! * verify()

    @Test
    @DisplayName("Test bird species statistics")
    void testGetBirdStatistics() {
        Map<BirdSpecies, Integer> expected = Map.of(        // ezt várom vissza
                BirdSpecies.SWALLOW, 1,
                BirdSpecies.BLACKBIRD, 2
        );
        when(birdDao.listBirds()).thenReturn(List.of(
                new Bird(BirdSpecies.BLACKBIRD),
                new Bird(BirdSpecies.SWALLOW),
                new Bird(BirdSpecies.BLACKBIRD)
        ));

        assertEquals(expected, birdService.getBirdStatistics());
        verify(birdDao).listBirds();
    }
}
