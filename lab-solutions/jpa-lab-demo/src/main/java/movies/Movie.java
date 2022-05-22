package movies;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    // ha csak @Id annotáció van, akkor nekem kell generálnom az id-t
//    @GeneratedValue(strategy = GenerationType.IDENTITY)     // Hibernate-től teljesen független, az oszlopnak az adatbázis automatikusan ad értéket
//    private Long id;            // @GeneratedValue esetén AUTO lesz, s a Hibernate létrehoz egy hibernate_sequence táblát
    private String id;

    private String title;

    private LocalDate releaseDate;

    private int length;

    @ElementCollection
    @CollectionTable(name = "ratings",
            joinColumns = @JoinColumn(name = "movie_id"))
    private List<Rating> ratings = new ArrayList<>();

    @Embedded
    private Director director;

    public Movie() {
    }

    public Movie(String title, LocalDate releaseDate, int length) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.releaseDate = releaseDate;
        this.length = length;
    }

//    public Movie(Long id, String title, LocalDate releaseDate, int length) {
//        this.id = id;
//        this.title = title;
//        this.releaseDate = releaseDate;
//        this.length = length;
//    }

    public void addRating(Rating rating) {
        ratings.add(rating);
    }

//    public Long getId() {
//        return id;
//    }

    public String getId() {
        return id;
    }

//    public void setId(Long id) {
//        this.id = id;
//    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getLength() {
        return length;
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

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }
}
