package locations;

public class LocationParser {

    public Location parse(String text) {
        try {
            String[] parts = text.split(",");
            String name = parts[0];
            Double latitude = Double.parseDouble(parts[1]);
            Double longitude = Double.parseDouble(parts[2]);
            return new Location(name, latitude, longitude);
        } catch (NullPointerException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Invalid text parameter!", e);
        }
    }
}
