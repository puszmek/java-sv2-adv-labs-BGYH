package team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class TeamRepository {

    private EntityManagerFactory factory;

    public TeamRepository(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public Team saveTeam(Team team) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(team);
        em.getTransaction().commit();
        em.close();
        return team;
    }

    public Team findTeamById(long id) {
        EntityManager em = factory.createEntityManager();
        Team team = em.find(Team.class, id);
        em.close();
        return team;
    }

    public Team updateBudget(long teamId, int price) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Team team = em.find(Team.class, teamId);
        team.setBudget(price);
        em.getTransaction().commit();
        em.close();
        return team;
    }

    public Team findTeamByNameWithPlayers(String name) {
        EntityManager entityManager = factory.createEntityManager();
        Team team = entityManager.createQuery("select distinct t from Team t left join fetch t.players where t.name = :name", Team.class)
                .setParameter("name", name)
                .getSingleResult();
        entityManager.close();
        return team;
    }

    public Team addScoresToTeam(long teamId, int scores) {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        Team team = entityManager.find(Team.class, teamId);
        team.setScores(team.getScores() + scores);
        entityManager.getTransaction().commit();
        entityManager.close();
        return team;
    }

    public List<Team> findTeamsByCountryAndTeamClass(String country, TeamClass teamClass) {
        EntityManager entityManager = factory.createEntityManager();
        List<Team> result = entityManager.createQuery("select t from Team t where t.country = :country and t.teamClass = :teamClass order by t.points desc ", Team.class)
                .setParameter("country", country)
                .setParameter("teamClass", teamClass)
                .getResultList();
        entityManager.close();
        return result;
    }
}
