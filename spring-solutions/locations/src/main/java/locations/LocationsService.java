package locations;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//@Service
public class LocationsService {

    private List<Location> locations = Arrays.asList(
            new Location("First Location", 35.142536, 36.748596),
            new Location("Second Location", 37.142536, 45.635241)
    );

    public List<Location> getLocations() {
        return new ArrayList<>(locations);
    }
}
