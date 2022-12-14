package dreamjob.controller;

import dreamjob.model.Candidate;
import dreamjob.model.User;
import dreamjob.service.CandidateService;
import dreamjob.service.CityService;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

import static dreamjob.util.HttpSessionUtil.setGuest;

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
    public String addCandidate(Model model, HttpSession session) {
        model.addAttribute("candidate",
                new Candidate(0, "Введите имя кандидата", "Заполните описание", LocalDateTime.now()));
        model.addAttribute("cities", cityService.getAllCities());
        setGuest(model, session);
        return "addCandidate";
    }

    @GetMapping("/candidates")
    public String candidates(Model model, HttpSession session) {
        model.addAttribute("candidates", candidateService.findAll());
        model.addAttribute("cities", cityService.getAllCities());
        setGuest(model, session);
        return "candidates";
    }

    @PostMapping("/createCandidate")
    public String createCandidate(@ModelAttribute Candidate candidate,
                                  @RequestParam("file") MultipartFile file) throws IOException {
        candidate.setPhoto(file.getBytes());
        candidate.setCity(cityService.findById(candidate.getCity().getId()));
        candidateService.add(candidate);
        return "redirect:/candidates";
    }

    @GetMapping("/formUpdateCandidates/{candidateId}")
    public String formUpdateCandidate(Model model, @PathVariable("candidateId") int id, HttpSession session) {
        model.addAttribute("candidates", candidateService.findById(id));
        model.addAttribute("cities", cityService.getAllCities());
        User user = (User) session.getAttribute("user");
        setGuest(model, session);
        return "updateCandidate";
    }

    @PostMapping("/updateCandidate")
    public String updateCandidate(@ModelAttribute Candidate candidate,
                                  @RequestParam("file") MultipartFile file) throws IOException {
        candidate.setPhoto(file.getBytes());
        candidate.setCity(cityService.findById(candidate.getCity().getId()));
        candidateService.update(candidate);
        return "redirect:/candidates";
    }

    @GetMapping("/photoCandidate/{candidateId}")
    public ResponseEntity<Resource> download(@PathVariable("candidateId") Integer candidateId) {
        Candidate candidate = candidateService.findById(candidateId);
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(candidate.getPhoto().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(candidate.getPhoto()));
    }
}
