package jpa;

import javax.persistence.Entity;

@Entity
public class SwallowNest extends Nest {

    private int altitude;

    public SwallowNest() {
    }

    public SwallowNest(int altitude) {
        this.altitude = altitude;
    }

    public SwallowNest(int numberOfEggs, int altitude) {
        super(numberOfEggs);
        this.altitude = altitude;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }
}
