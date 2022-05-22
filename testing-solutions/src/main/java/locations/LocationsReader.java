package locations;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LocationsReader {

    public List<Location> readLocations(Path path) {
        List<Location> locations = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                Location location = parseLine(line);
                locations.add(location);
            }
//        try {
//            List<String> lines = Files.readAllLines(path);
//            locations = lines.stream()
//                    .map(this::parseLine)
//                    .toList();
            return locations;
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot open file for read!", ioe);
        }
    }

    private Location parseLine(String line) {
        String[] parts = line.split(",");
        String name = parts[0];
        double latitude = Double.parseDouble(parts[1]);
        double longitude = Double.parseDouble(parts[2]);
        return new Location(name, latitude, longitude);
    }

    public List<Location> filterLocationsBeyondArcticCircle(List<Location> locations) {
        return locations.stream()
                .filter(location -> location.getLatitude() > 66.57)
                .toList();
    }
}
