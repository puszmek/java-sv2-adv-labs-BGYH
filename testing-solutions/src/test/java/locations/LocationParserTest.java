package locations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationParserTest {

    LocationParser locationParser;
    String text;

    @BeforeEach
    void init() {
        locationParser = new LocationParser();
        text = "Budapest,47.497913,19.040236";
    }

    @Test
    @DisplayName("Test if Location is correctly parsed from text:")
    void testParse() {
        Location location = locationParser.parse(text);
        assertEquals("Budapest", location.getName());
        assertEquals(47.497913, location.getLatitude());
        assertEquals(19.040236, location.getLongitude());
    }

    @Test
    @DisplayName("Test if Location is correctly parsed from text in one assert:")
    void testParseAtOnce() {
        Location location = locationParser.parse(text);
        assertAll(
                () -> assertEquals("Budapest", location.getName()),
                () -> assertEquals(47.497913, location.getLatitude()),
                () -> assertEquals(19.040236, location.getLongitude())
        );
    }

    @Test
    @DisplayName("Test if Exception is thrown, when parameter is null:")
    void testParseWithNullParameter() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> locationParser.parse(null));
        assertEquals("Invalid text parameter!", exception.getMessage());
    }

    @Test
    @DisplayName("Test if Exception is thrown, when double numbers in parameter are wrong:")
    void testParseWithWrongParameterNotANumber() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> locationParser.parse("Budapest,47.49q7913,19.040236"));
        assertEquals("Invalid text parameter!", exception.getMessage());
    }

    @Test
    @DisplayName("Test if Exception is thrown, when parameter text has less than three parts:")
    void testParseWithWrongParameterLessParts() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> locationParser.parse("Budapest 47.497913 19.040236"));
        assertEquals("Invalid text parameter!", exception.getMessage());
    }

    @Test
    @DisplayName("Test if method returns two different objects, when invocated twice:")
    void testDifferentLocations() {
        Location location = locationParser.parse(text);
        Location location1 = locationParser.parse(text);
        assertNotEquals(location, location1);
        assertNotSame(location, location1);
    }
}