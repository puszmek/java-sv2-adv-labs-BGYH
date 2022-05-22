package locations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LocationsServiceTest {

    @Mock
    LocationsRepository locationsRepository;

    @InjectMocks
    LocationsService locationsService;

    @Test
    void testCalculateDistanceWithoutMockito() {
        LocationsRepository locationsRepository = new LocationsRepository() {
            @Override
            public Optional<Location> findByName(String name) {
                return Optional.empty();
            }
            @Override
            public Optional<Double> findLatitudeByName(String name) {
                return Optional.empty();
            }
        };
        LocationsService locationsService = new LocationsService(locationsRepository);
        assertEquals(Optional.empty(), locationsService.calculateDistance("Budapest", "Debrecen"));
    }

    @Test
    void testCalculateDistanceWithoutFirstLocation() {
        when(locationsRepository.findByName("Budapest")).thenReturn(
                Optional.empty()
        );
        when(locationsRepository.findByName("Debrecen")).thenReturn(
                Optional.of(new Location("Debrecen", 47.52997, 21.63916))
        );
        Optional<Double> result = locationsService.calculateDistance("Budapest", "Debrecen");
        assertEquals(Optional.empty(), result);
        verify(locationsRepository).findByName(argThat(l -> l.equals("Budapest")));
        verify(locationsRepository).findByName(argThat(l -> l.equals("Debrecen")));
    }

    @Test
    void testCalculateDistanceWithoutSecondLocation() {
        when(locationsRepository.findByName("Budapest")).thenReturn(
                Optional.of(new Location("Budapest", 47.49791, 19.04023))
        );
        when(locationsRepository.findByName("Debrecen")).thenReturn(
                Optional.empty()
        );
        Optional<Double> result = locationsService.calculateDistance("Budapest", "Debrecen");
        assertEquals(Optional.empty(), result);
        verify(locationsRepository).findByName(argThat(l -> l.equals("Budapest")));
        verify(locationsRepository).findByName(argThat(l -> l.equals("Debrecen")));
    }

    @Test
    void testCalculateDistanceWithSameLocations() {
        when(locationsRepository.findByName("Budapest")).thenReturn(
                Optional.of(new Location("Budapest", 47.49791, 19.04023))
        );
        Optional<Double> result = locationsService.calculateDistance("Budapest", "Budapest");
        assertEquals(Optional.of(0d), result);
        verify(locationsRepository,times(2)).findByName(argThat(l -> l.equals("Budapest")));
    }

    @Test
    void testCalculateDistanceWithDifferentLocations() {
        when(locationsRepository.findByName("Budapest")).thenReturn(
                Optional.of(new Location("Budapest", 47.49791, 19.04023))
        );
        when(locationsRepository.findByName("Debrecen")).thenReturn(
                Optional.of(new Location("Debrecen", 47.52997, 21.63916))
        );
        Double result = locationsService.calculateDistance("Budapest", "Debrecen").get();
        assertEquals(195.2d, result, 0.05);
        verify(locationsRepository).findByName(argThat(l -> l.equals("Budapest")));
        verify(locationsRepository).findByName(argThat(l -> l.equals("Debrecen")));
    }

    @Test
    void testIsOnNorthernHemisphereTrue() {
        when(locationsRepository.findLatitudeByName(any())).thenReturn(
                Optional.of(42d)
        );
        boolean result = locationsService.isOnNorthernHemisphere("Debrecen");
        assertEquals(true, result);
        verify(locationsRepository).findLatitudeByName("Debrecen");
    }

    @Test
    void testIsOnNorthernHemisphereFalse() {
        when(locationsRepository.findLatitudeByName(any())).thenReturn(
                Optional.of(-42d)
        );
        boolean result = locationsService.isOnNorthernHemisphere("Debrecen");
        assertEquals(false, result);
        verify(locationsRepository).findLatitudeByName("Debrecen");
    }

    @Test
    void testIsOnNorthernHemisphere2() {
        when(locationsRepository.findLatitudeByName(any())).thenReturn(
                Optional.empty()
        );
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> locationsService.isOnNorthernHemisphere("Debrecen"));
        assertEquals("Cannot find location with name!", exception.getMessage());
        verify(locationsRepository).findLatitudeByName("Debrecen");
    }
}