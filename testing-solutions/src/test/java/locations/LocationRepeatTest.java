package locations;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationRepeatTest {

    private double[][] values = {
            {23.121212, 12.232323, 0},
            {0, -12.232323, 1},
            {0, 65.545454, 1},
            {-7.868686, -10.242424, 0}
    };

    @RepeatedTest(value = 4, name = "{currentRepetition} / {totalRepetitions}")
    void testEquator(RepetitionInfo info) {
        Location location = new Location(
                "Town",
                values[info.getCurrentRepetition() - 1][0],
                values[info.getCurrentRepetition() - 1][1]
        );
        assertEquals(values[info.getCurrentRepetition() - 1][2] == 1, location.isOnEquator());
    }

    @ParameterizedTest(name = "Latitude = {0}, Longitude = {1}, Expected = {2}")
    @MethodSource("getLocations")
    void testPrimeMeridian(int latitude, int longitude, boolean expected) {
        Location location = new Location("Town", latitude, longitude);
        assertEquals(expected, location.isOnPrimeMeridian());
    }

    static Stream<Arguments> getLocations() {
        return Stream.of(
                Arguments.arguments(0, 23, false),
                Arguments.arguments(12, 0, true),
                Arguments.arguments(65, -10, false),
                Arguments.arguments(-7, 0, true)
        );
    }

    @ParameterizedTest(name = "Latitude = {0}, Longitude = {1}, Expected = {2}")
    @CsvFileSource(resources = "/location.csv")
    void testPrimeMeridianFromFile(int latitude, int longitude, boolean expected) {
        Location location = new Location("Town", latitude, longitude);
        assertEquals(expected, location.isOnPrimeMeridian());
    }
}
