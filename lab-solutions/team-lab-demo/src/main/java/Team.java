import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teams")
public class Team {

    public enum TeamType {FIRST_DIVISION, SECOND_DIVISION, THIRD_DIVISION}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String country;

    @Enumerated(EnumType.STRING)
    @Column(name = "team_type")
    private TeamType teamType;

    private int score;

    @ElementCollection
    @CollectionTable(name = "players", joinColumns = @JoinColumn(name = "team_name"))
    @Column(name = "player")
    private List<String> players = new ArrayList<>();

    public Team() {
    }

    public Team(String name, String country, TeamType teamType, int score) {
        this.name = name;
        this.country = country;
        this.teamType = teamType;
        this.score = score;
    }

    public void addPlayer(String player) {
        players.add(player);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public TeamType getTeamType() {
        return teamType;
    }

    public void setTeamType(TeamType teamType) {
        this.teamType = teamType;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", teamType=" + teamType +
                ", score=" + score +
                ", players=" + players +
                '}';
    }
}
