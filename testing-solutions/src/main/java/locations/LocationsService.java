package locations;

import java.util.Optional;

public class LocationsService {

    private LocationsRepository locationsRepository;

    public LocationsService(LocationsRepository locationsRepository) {
        this.locationsRepository = locationsRepository;
    }

    public Optional<Double> calculateDistance(String name1, String name2) {
        Optional<Location> firstOptional = locationsRepository.findByName(name1);
        Optional<Location> secondOptional = locationsRepository.findByName(name2);
        if (firstOptional.isEmpty() || secondOptional.isEmpty()) {
            return Optional.empty();
        } else {
            Location firstLocation = firstOptional.get();
            Location secondLocation = secondOptional.get();
            return Optional.of(firstLocation.distanceFrom(secondLocation));
        }
    }

    public boolean isOnNorthernHemisphere(String name) {
        Optional<Double> optionalLatitude = locationsRepository.findLatitudeByName(name);
        if (optionalLatitude.isEmpty()) {
            throw new IllegalArgumentException("Cannot find location with name!");
        } else {
            return optionalLatitude.get() > 0d;
        }
    }
}
