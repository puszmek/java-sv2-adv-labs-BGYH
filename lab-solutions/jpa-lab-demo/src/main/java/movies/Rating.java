package movies;

import javax.persistence.Embeddable;

@Embeddable
public class Rating {

    private double value;

    private String username;

    public Rating() {
    }

    public Rating(double value, String username) {
        this.value = value;
        this.username = username;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "value=" + value +
                ", username='" + username + '\'' +
                '}';
    }
}
