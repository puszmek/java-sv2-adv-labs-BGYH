package jpa;

import javax.persistence.Entity;

@Entity
public class NestingBox extends  Nest {

    private int height;

    private int width;

    public NestingBox() {
    }

    public NestingBox(int height) {
        this.height = height;
    }

    public NestingBox(int numberOfEggs, int height) {
        super(numberOfEggs);
        this.height = height;
    }

    public NestingBox(int numberOfEggs, int height, int width) {
        super(numberOfEggs);
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
