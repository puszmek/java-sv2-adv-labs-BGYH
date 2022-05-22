package team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class PlayerRepository {

    private EntityManagerFactory factory;

    public PlayerRepository(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public Player savePlayer(Player player) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(player);
        em.getTransaction().commit();
        em.close();
        return player;
    }

    public Player savePlayerWithTeam(Player player, long teamId) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Team team = em.getReference(Team.class, teamId);
        player.setTeam(team);
        em.persist(player);
        em.getTransaction().commit();
        em.close();
        return player;
    }

    public Player findById(long playerId) {
        EntityManager em = factory.createEntityManager();
        Player player = em.find(Player.class, playerId);
        em.close();
        return player;
    }

    public Player updatePlayerTeam(long playerId, long teamId) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Team team = em.getReference(Team.class, teamId);
        Player player = em.getReference(Player.class, playerId);
        player.setTeam(team);
        em.getTransaction().commit();
        em.close();
        return player;
    }
}
