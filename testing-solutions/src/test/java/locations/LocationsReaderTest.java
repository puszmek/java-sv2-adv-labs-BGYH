package locations;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@ExtendWith(SoftAssertionsExtension.class)
public class LocationsReaderTest {

    LocationsReader locationsReader;
    List<Location> locations;

    @BeforeEach
    void init() {
        locationsReader = new LocationsReader();
        locations = locationsReader.readLocations(Path.of("src/test/resources/favouritelocations.csv"));
    }

    @Test
    void testReadLocationsWithName() {
        assertThat(locations)
                .hasSize(7)
                .extracting(Location::getName)
                .contains("Budapest", "Debrecen", "Pécs", "Győr", "Szeged");
    }

    @Test
    void testReadLocationsWithWrongValue() {
        assertThat(locations)
                .extracting("name", "latitude", "longitude")
                .doesNotContain(tuple("Budapest", 47.497912, 0));
    }

    @Test
    void filterLocationsBeyondArcticCircle() {
        List<Location> filteredLocations = locationsReader.filterLocationsBeyondArcticCircle(locations);
        assertThat(filteredLocations)
                .hasSize(2)
                .extracting(Location::getName)
                .contains("Tiksi")
                .doesNotContain("Pécs")
                .containsOnly("Tiksi", "Town");
        assertThat(filteredLocations)
                .filteredOn(location -> location.getLatitude() == location.getLongitude())
                .extracting(Location::getName, Location::getLatitude)
                .containsOnly(tuple("Town", 80d));
    }

    @Test
    void filterLocationsBeyondArcticCircleWithNameAbc(SoftAssertions softAssertions) {
        Location location = new Location("Abc", 50.111111, 60.222222);

//        assertThat(location.getName())
//                .startsWith("b")
//                .endsWith("b");

//        SoftAssertions softAssertions = new SoftAssertions();
//        softAssertions.assertThat(location.getName()).startsWith("b");
//        softAssertions.assertThat(location.getName()).endsWith("b");
//        softAssertions.assertAll();

//        softAssertions.assertThat(location.getName()).startsWith("b");
//        softAssertions.assertThat(location.getName()).endsWith("b");
    }
}
