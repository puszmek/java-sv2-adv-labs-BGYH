package locations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LocationsControllerIT {

    @Autowired
    LocationsController locationsController;

    @Test
    void testGetLocations() {
        String result = locationsController.getLocations();

        assertThat(result).startsWith("[Location{name: First Location");
    }
}