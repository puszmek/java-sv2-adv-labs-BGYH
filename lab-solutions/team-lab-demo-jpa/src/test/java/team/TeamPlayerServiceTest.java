package team;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

class TeamPlayerServiceTest {

    EntityManagerFactory factory;
    TeamRepository teamRepository;
    PlayerRepository playerRepository;
    TeamPlayerService service;

    @BeforeEach
    void init() {
        factory = Persistence.createEntityManagerFactory("pu");
        teamRepository = new TeamRepository(factory);
        playerRepository = new PlayerRepository(factory);
        service = new TeamPlayerService(teamRepository, playerRepository);
    }

    @Test
    void testTransfer() {
        Team team = teamRepository.saveTeam(new Team("Chelsae", "England", TeamClass.FIRST_DIVISION, 10_000_000));
        Player player = playerRepository.savePlayer(new Player("John", 120));

        service.transferPlayer(team.getId(), player.getId());

        Team team2 = teamRepository.saveTeam(new Team("Arsenal", "England", TeamClass.FIRST_DIVISION, 10_000_000));

        service.transferPlayer(team2.getId(), player.getId());

        Team team3 = teamRepository.saveTeam(new Team("Vasas", "Hungary", TeamClass.SECOND_DIVISION, 10_000));

        service.transferPlayer(team3.getId(), player.getId());
    }
}