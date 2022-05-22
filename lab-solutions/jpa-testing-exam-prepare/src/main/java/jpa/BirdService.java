package jpa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BirdService {

    private BirdDao birdDao;

    public BirdService(BirdDao birdDao) {
        this.birdDao = birdDao;
    }

    // egy adatszerkezetben visszaadja, hogy az egyes fajtájú madarakból hány található az adatbázisban
    public Map<BirdSpecies, Integer> getBirdStatistics() {
        List<Bird> birds = birdDao.listBirds();
        Map<BirdSpecies, Integer> result = new HashMap<>();
        for (Bird actual : birds) {
            if (!result.containsKey(actual.getSpecies())) {
                result.put(actual.getSpecies(), 0);
            }
            result.put(actual.getSpecies(), result.get(actual.getSpecies()) + 1);
        }
        return result;
    }
}
