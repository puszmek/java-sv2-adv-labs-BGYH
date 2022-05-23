package locations;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class LocationsController {

    private List<Location> locations = Arrays.asList(
            new Location("First Location", 25.142536, 36.748596),
            new Location("Second Location", 47.142536, 45.635241)
    );

    @GetMapping("/")      // ("/locations")
    public String getLocations() {
        return locations.toString();
    }
}
