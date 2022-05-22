package locations;

import java.util.List;

public class LocationOperators {

    public List<Location> filterOnNorth(List<Location> locations) {
        return locations.stream()
                .filter(location -> location.getLatitude() > 0)
                .toList();
    }
}
