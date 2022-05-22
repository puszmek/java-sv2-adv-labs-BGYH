package movies;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    private String id;

    private String title;

    @Column(name = "dat_of_release")
    private LocalDate releaseDate;

    private int length;

    @OneToMany(mappedBy = "movie")
    private List<Rating> ratings = new ArrayList<>();

    @ManyToMany
    private Set<Actor> actors = new HashSet<>();

    public Movie() {
    }

    public Movie(String title, LocalDate releaseDate, int length) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.releaseDate = releaseDate;
        this.length = length;
    }

    public void addRating(Rating rating) {
        ratings.add(rating);
    }

    public void addActor(Actor actor) {
        actors.add(actor);
        actor.getMovies().add(this);
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public int getLength() {
        return length;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public Set<Actor> getActors() {
        return actors;
    }

    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }
}