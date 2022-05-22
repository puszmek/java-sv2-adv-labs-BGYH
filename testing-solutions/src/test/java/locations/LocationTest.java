package locations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LocationTest {

    Location location;
    LocationParser locationParser;

    @BeforeEach
    void init() {
        locationParser = new LocationParser();
        location = locationParser.parse("Budapest,47.497913,19.040236");
    }

    @Test
    @DisplayName("Test if Location is correctly created:")
    void testCreateLocation() {
        assertEquals("Budapest", location.getName());
        assertEquals(47.497913, location.getLatitude());
        assertEquals(19.040236, location.getLongitude());
    }

    @Test
    @DisplayName("Test if method returns true, when town is on the Equator:")
    void testIsOnEquator() {
        location = new Location("PlaceInUganda", 0d, 31.146972);
        assertTrue(location.isOnEquator());
    }

    @Test
    @DisplayName("Test if method returns false, when town is not on the Equator:")
    void testIsNotOnEquator() {
        assertFalse(location.isOnEquator());
    }

    @Test
    @DisplayName("Test if method returns true, when town is on the Prime Meridian:")
    void testIsOnPrimeMeridian() {
        location = new Location("PlaceInMali", 17.362960, 0d);
        assertTrue(location.isOnPrimeMeridian());
    }

    @Test
    @DisplayName("Test if method returns false, when town is not on the Prime Meridian:")
    void testIsNotOnPrimeMeridian() {
        assertFalse(location.isOnPrimeMeridian());
    }

    @Test
    @DisplayName("Test if method returns correctly distance from Location:")
    void testDistanceFromLocation() {
        Location location1 = locationParser.parse("Debrecen,47.52997,21.63916");
        assertEquals(195.2, location.distanceFrom(location1), 0.05);
    }

    @Test
    @DisplayName("Test if distance is zero:")
    void testDistanceFromLocationIsZero() {
        Location location1 = locationParser.parse("Budapest,47.497913,19.040236");
        assertEquals(0.0, location.distanceFrom(location1));
    }

    @Test
    @DisplayName("Test if method returns two different or same objects, when invocated twice:")
    void testDifferentLocations() {
        Location firstLocation = new Location("Budapest", 47.497913, 19.040236);
        Location secondLocation = new Location("Budapest", 47.497913, 19.040236);
        assertAll(
                () -> assertEquals(firstLocation, secondLocation),
                () -> assertNotSame(firstLocation, secondLocation)
        );
    }

    @Test
    @DisplayName("Test if Exception is thrown, when latitude is smaller than -90:")
    void testCreateLocationWithTooLowLatitude() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Location("TownWithTooLowLatitude", -90.1, 23.121212));
        assertEquals("Latitude or longitude are not from interval [-90, 90] or [-180, 180]!", exception.getMessage());
    }

    @Test
    @DisplayName("Test if Exception is thrown, when latitude is bigger than 90:")
    void testCreateLocationWithTooHighLatitude() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Location("TownWithTooHighLatitude", 90.1, 23.121212));
        assertEquals("Latitude or longitude are not from interval [-90, 90] or [-180, 180]!", exception.getMessage());
    }

    @Test
    @DisplayName("Test if Exception is thrown, when longitude is smaller than -180:")
    void testCreateLocationWithTooLowLongitude() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Location("TownWithTooLowLongitude", 23.121212, -180.1));
        assertEquals("Latitude or longitude are not from interval [-90, 90] or [-180, 180]!", exception.getMessage());
    }

    @Test
    @DisplayName("Test if Exception is thrown, when longitude is bigger than 180:")
    void testCreateLocationWithTooHighLongitude() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Location("TownWithTooHighLongitude", 23.121212, 180.1));
        assertEquals("Latitude or longitude are not from interval [-90, 90] or [-180, 180]!", exception.getMessage());
    }
}
