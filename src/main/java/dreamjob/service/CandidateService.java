package dreamjob.service;

import dreamjob.model.Candidate;
import dreamjob.model.Post;
import dreamjob.store.CandidateStore;
import dreamjob.store.PostStore;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CandidateService {

    private final CandidateStore candidateStore;

    public CandidateService(CandidateStore store) {
        this.candidateStore = store;
    }

    public Collection<Candidate> findAll() {
        return candidateStore.findAll();
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
