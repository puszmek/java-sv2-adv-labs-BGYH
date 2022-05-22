package locations;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LocationsController {

    private final List<Location> locations = new ArrayList<>(List.of(
            new Location(1L, "First Location", 35.142536, 18.635241),
            new Location(2L, "Second Location", 24.475869, 47.695847))
    );

    @GetMapping("/")
    @ResponseBody
    public String getLocations() {
        return locations.toString();
    }
}
