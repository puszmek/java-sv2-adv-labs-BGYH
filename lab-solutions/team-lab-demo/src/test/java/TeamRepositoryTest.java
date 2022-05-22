import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class TeamRepositoryTest {

    EntityManagerFactory factory;

    TeamRepository repository;

    Team team;

    @BeforeEach
    void init() {
        factory = Persistence.createEntityManagerFactory("pu");
        repository = new TeamRepository(factory);

        team = new Team("TeamDoe", "Italy", Team.TeamType.SECOND_DIVISION, 45);
        team.addPlayer("John Doe");
        team.addPlayer("Jack Doe");
        team.addPlayer("Jane Doe");
    }

    @AfterEach
    void close() {
        factory.close();
    }

    @Test
    void testSaveTeam() {
        repository.saveTeam(team);

        assertThat(team).isNotNull();
        assertThat(team)
                .extracting(Team::getName)
                .isEqualTo("TeamDoe");
        assertThat(team.getPlayers())
                .hasSize(3)
                .contains("Jack Doe")
                .doesNotContain("John Do");
    }

    @Test
    void testFindTeamById() {
        repository.saveTeam(team);

        Team result = repository.findTeamById(team.getId());

        assertThat(result).extracting(Team::getName).isEqualTo("TeamDoe");
        assertThat(result).extracting(Team::getScore).isEqualTo(45);
        assertThat(team.getTeamType()).isNotEqualTo(Team.TeamType.THIRD_DIVISION);
    }

    @Test
    void testFindTeamByNameWithPlayers() {
        team.setPlayers(List.of("Jim Doe", "Judy Doe"));
        repository.saveTeam(team);

        Team result = repository.findTeamByNameWithPlayers(team.getName());

        assertThat(result).extracting(Team::getName).isEqualTo("TeamDoe");
        assertThat(result.getPlayers())
                .hasSize(2)
                .contains("Jim Doe", "Judy Doe")
                .doesNotContain("Jane Doe");
    }

    @Test
    void testUpdateTeamsScores() {
        repository.saveTeam(team);
        repository.updateTeamsScores(team.getId(), 10);

        Team result = repository.findTeamById(team.getId());

        assertThat(result.getScore()).isEqualTo(55);
    }

    @Test
    void testFindAllTeamsByCountryAndTeamTypeWithScoresInDescendingOrder() {
        repository.saveTeam(team);

        Team team2 = new Team("TeamBoe", "Italy", Team.TeamType.FIRST_DIVISION, 42);
//        team2.addPlayer("John Boe");
//        team2.addPlayer("Jack Boe");
        repository.saveTeam(team2);

        Team team3 = new Team("TeamPoe", "Germany", Team.TeamType.SECOND_DIVISION, 42);
//        team3.addPlayer("John Poe");
//        team3.addPlayer("Jack Poe");
        repository.saveTeam(team3);

        Team team4 = new Team("TeamMoe", "Italy", Team.TeamType.SECOND_DIVISION, 48);
//        team4.addPlayer("Jane Moe");
        repository.saveTeam(team4);

        List<Team> result = repository.findAllTeamsByCountryAndTeamTypeWithScoresInDescendingOrder("Italy", Team.TeamType.SECOND_DIVISION);

        assertThat(result)
                .hasSize(2)
                .extracting(Team::getName, Team::getCountry, Team::getTeamType)
                .containsExactly(tuple("TeamMoe", "Italy", Team.TeamType.SECOND_DIVISION), tuple("TeamDoe", "Italy", Team.TeamType.SECOND_DIVISION));
        assertThat(result.get(0).getScore()).isEqualTo(48);
        assertThat(result).doesNotContain(team2, team3);
    }
}