package locations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LocationOperatorsTest {

    List<Location> locations = new ArrayList<>();
    LocationOperators locationOperators = new LocationOperators();

    @BeforeEach
    void init() {
        locations.add(new Location("Budapest", 47.49791, 19.04023));
        locations.add(new Location("Debrecen", 47.52997, 21.63916));
        locations.add(new Location("Buenos Aires", -34.603722, -58.381592));
    }

    @Test
    @DisplayName("Test wich cities are on northern hemisphere:")
    void testNorthLocations() {
        List<String> result = locationOperators.filterOnNorth(locations).stream()
                .map(Location::getName)
                .toList();
        assertEquals(List.of("Budapest", "Debrecen"), result);
    }
}