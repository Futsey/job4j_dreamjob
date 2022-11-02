package dreamjob.controller;

import dreamjob.model.Candidate;
import dreamjob.service.CandidateService;
import dreamjob.service.CityService;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@ThreadSafe
@Controller
public class CandidateController {

    private final CandidateService candidateService;
    private final CityService cityService;

    public CandidateController(CandidateService candidateService, CityService cityService) {
        this.candidateService = candidateService;
        this.cityService = cityService;
    }

    @GetMapping("/formAddCandidate")
    public String addCandidate(Model model) {
        model.addAttribute("candidate",
                new Candidate(0, "Введите имя кандидата", "Заполните описание", LocalDateTime.now()));
        model.addAttribute("cities", cityService.getAllCities());
        return "addCandidate";
    }

    @GetMapping("/candidates")
    public String candidates(Model model) {
        model.addAttribute("candidates", candidateService.findAll());
        return "candidates";
    }

    @PostMapping("/createCandidate")
    public String createCandidate(@ModelAttribute Candidate candidate) {
        candidateService.add(candidate);
        return "redirect:/candidates";
    }

    @GetMapping("/formUpdateCandidates/{candidateId}")
    public String formUpdateCandidate(Model model, @PathVariable("candidateId") int id) {
        model.addAttribute("candidates", candidateService.findById(id));
        model.addAttribute("cities", cityService.getAllCities());
        return "updateCandidate";
    }

    @PostMapping("/updateCandidate")
    public String updateCandidate(@ModelAttribute Candidate candidate) {
        candidateService.update(candidate);
        return "redirect:/candidates";
    }
}
