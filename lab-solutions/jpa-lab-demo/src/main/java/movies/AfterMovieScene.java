package movies;

import javax.persistence.*;

@Entity
@Table(name = "after_movie_scenes")
public class AfterMovieScene {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double length;

    private String description;

    @ManyToOne
    private Movie movie;

    public AfterMovieScene() {
    }

    public AfterMovieScene(double length, String description) {
        this.length = length;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
