package locations;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LocationsServiceTest {

    @Test
    void testGetLocations() {
        LocationsService locationsService = new LocationsService();
        List<Location> result = locationsService.getLocations();

        assertThat(result)
                .hasSize(2)
                .extracting(Location::getName)
                .containsExactly("First Location", "Second Location");
        assertThat(result.get(0))
                .extracting(Location::getLat)
                .isEqualTo(35.142536);
    }
}