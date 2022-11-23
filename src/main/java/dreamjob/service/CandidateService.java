package dreamjob.service;

import dreamjob.model.Candidate;
import dreamjob.store.CandidateDBStore;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;

import java.util.List;

@ThreadSafe
@Service
public class CandidateService {

    private final CandidateDBStore candidateStore;
    private final CityService cityService;

    public CandidateService(CandidateDBStore candidateStore, CityService cityService) {
        this.candidateStore = candidateStore;
        this.cityService = cityService;
    }

    public List<Candidate> findAll() {
        List<Candidate> candidates = candidateStore.findAll();
        candidates.forEach(
                post -> post.setCity(
                        cityService.findById(post.getCity().getId())
                )
        );
        return candidates;
    }

    public void add(Candidate candidate) {
        candidateStore.add(candidate);
    }

    public Candidate findById(int id) {
        return candidateStore.findById(id);
    }

    public void update(Candidate candidate) {
        candidateStore.update(candidate);
    }
}
