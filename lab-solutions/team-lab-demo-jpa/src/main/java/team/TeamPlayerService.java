package team;

public class TeamPlayerService {

    private TeamRepository teamRepository;
    private PlayerRepository playerRepository;

    public TeamPlayerService(TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    public void transferPlayer(long teamId, long playerId) {
        Team team = teamRepository.findTeamById(teamId);
        Player player = playerRepository.findById(playerId);

        if (player.getTeam() == null) {
            playerRepository.updatePlayerTeam(playerId, teamId);
        } else if (player.getValue() < (team.getBudget() * 20.0) / 100.0) {
            playerRepository.updatePlayerTeam(playerId, teamId);
            teamRepository.updateBudget(teamId, team.getBudget() - player.getValue());
        } else {
            throw new IllegalArgumentException("Cannot buy Player!");
        }
    }
}
