package app.zhc1.glossary.controller;

import app.zhc1.glossary.domain.Term;
import app.zhc1.glossary.service.TermService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TermController {
    private final TermService termService;

    @GetMapping("/api/v1/terms/{query}")
    public List<Term> query(@PathVariable String query) {
        return termService.query(query);
    }

    @PostMapping("/api/v1/terms")
    public Term add(@RequestBody AddTermRequest request) {
        Term term = new Term(request.title(), request.definition());
        return termService.add(term);
    }

    public record AddTermRequest(String title, String definition) {}
}
