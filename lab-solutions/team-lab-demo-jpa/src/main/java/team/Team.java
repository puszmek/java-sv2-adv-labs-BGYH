package team;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String country;

    @Enumerated(EnumType.STRING)
    @Column(name = "team_class")
    private TeamClass teamClass;

    @OneToMany(mappedBy = "team")
    private List<Player> players = new ArrayList<>();

    private int scores;

    private int budget;

    public Team() {
    }

    public Team(String name, String country, TeamClass teamClass, int budget) {
        this.name = name;
        this.country = country;
        this.teamClass = teamClass;
        this.budget = budget;
    }

    public void addPlayer(Player player) {
        players.add(player);
        player.setTeam(this);
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
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

    public TeamClass getTeamClass() {
        return teamClass;
    }

    public void setTeamClass(TeamClass teamClass) {
        this.teamClass = teamClass;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }
}
