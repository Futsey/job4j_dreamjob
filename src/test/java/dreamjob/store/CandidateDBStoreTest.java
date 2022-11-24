package dreamjob.store;

import dreamjob.Main;
import dreamjob.model.Candidate;
import dreamjob.model.City;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CandidateDBStoreTest {

    @Test
    public void whenCreateCandidate() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        Candidate candidate = new Candidate(1,
                "Andrew",
                "Junior",
                LocalDateTime.now(),
                new City(1, "Москва"));
        store.add(candidate);
        Candidate candidateInDb = store.findById(candidate.getId());
        assertThat(candidateInDb.getName(), is(candidate.getName()));
    }

    @Test
    public void whenFindCandidateById() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        Candidate candidate = new Candidate(1,
                "Andrew",
                "Junior",
                LocalDateTime.now(),
                new City(1, "Москва"));
        store.add(candidate);
        Candidate candidateInDb = store.findById(candidate.getId());
        assertThat(candidateInDb.getId(), is(candidate.getId()));
    }

    @Test
    public void whenUpdateCandidateName() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        Candidate candidate = new Candidate(1,
                "Andrew",
                "Junior",
                LocalDateTime.now(),
                new City(1, "Москва"));
        store.add(candidate);
        candidate.setName("Weak Junior");
        store.update(candidate);
        Candidate postInDb = store.findById(candidate.getId());
        assertThat(postInDb.getName(), is(candidate.getName()));
    }

    @Test
    public void whenUpdateCandidateDesc() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        Candidate candidate = new Candidate(1,
                "Andrew",
                "Junior",
                LocalDateTime.now(),
                new City(1, "Москва"));
        store.add(candidate);
        candidate.setDescription("Really Weak Junior");
        store.update(candidate);
        Candidate postInDb = store.findById(candidate.getId());
        assertThat(postInDb.getDescription(), is(candidate.getDescription()));
    }

    @Test
    public void whenUpdatePCandidateCreateDate() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        Candidate candidate = new Candidate(1,
                "Andrew",
                "Junior",
                LocalDateTime.now(),
                new City(1, "Москва"));
        store.add(candidate);
        candidate.setCity(new City(2, "СПб"));
        store.update(candidate);
        Candidate postInDb = store.findById(candidate.getId());
        assertThat(postInDb.getCity(), is(candidate.getCity()));
    }

    @Test
    public void whenFindAllCandidates() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        Candidate candidate = new Candidate(1,
                "Andrew",
                "Junior",
                LocalDateTime.now(),
                new City(1, "Москва"));
        Candidate candidate2 = new Candidate(2,
                "Ira",
                "Senior",
                LocalDateTime.now(),
                new City(2, "СПб"));
        store.add(candidate);
        store.add(candidate2);
        Candidate postInDb = store.findById(candidate.getId());
        Candidate postInDb2 = store.findById(candidate2.getId());
        CandidateDBStore storeDB = new CandidateDBStore(new Main().loadPool());
        storeDB.add(postInDb);
        storeDB.add(postInDb2);
        assertThat(store.findAll(), is(storeDB.findAll()));
    }
}