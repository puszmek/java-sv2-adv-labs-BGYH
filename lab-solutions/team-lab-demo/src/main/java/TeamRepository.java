import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TeamRepository {

    private EntityManagerFactory factory;

    public TeamRepository(EntityManagerFactory factory) {
        this.factory = factory;
    }

    private TeamRepository repository;

    // csapat lementése
    public void saveTeam(Team team) {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(team);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Team findTeamById(long id) {
        EntityManager em = factory.createEntityManager();
        Team team;
        try {
            team = em.find(Team.class, id);
            return team;
        } finally {
            em.close();
        }
    }

    // csapat lekérdezése játékosokkal együtt csapatnév alapján
    public Team findTeamByNameWithPlayers(String name) {
        EntityManager em = factory.createEntityManager();
        Team team;
        try {
            return team = em.createQuery("select t from Team t join fetch t.players where name = :name", Team.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    // egy csapat pontszámának frissítése a kapott értékre id alapján
    public void updateTeamsScores(Long id, int score) {
        EntityManager em = factory.createEntityManager();
        List<Team> teams;
        try {
            teams = em.createQuery("select t from Team t order by t.id", Team.class)
                    .getResultList();
            em.getTransaction().begin();
            for (Team actual : teams) {
                actual.setScore(actual.getScore() + score);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // metódus, ami ország és osztály alapján visszaadja az összes csapatot pontszám alapján csökkenő sorrendben
    public List<Team> findAllTeamsByCountryAndTeamTypeWithScoresInDescendingOrder(String country, Team.TeamType type) {
        EntityManager em = factory.createEntityManager();
        List<Team> teams;
        try {
            return teams = em.createQuery("select t from Team t where country = :country and teamType = :teamType order by t.score DESC", Team.class)
                    .setParameter("country", country)
                    .setParameter("teamType", type)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
