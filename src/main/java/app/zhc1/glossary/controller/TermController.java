package app.zhc1.glossary.controller;

import static java.util.stream.Collectors.*;

import app.zhc1.glossary.controller.request.TermCreateRequest;
import app.zhc1.glossary.controller.response.AutocompleteKeywords;
import app.zhc1.glossary.domain.Term;
import app.zhc1.glossary.service.TermService;
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

    @PostMapping("/api/v1/terms")
    public Term add(@RequestBody TermCreateRequest request) {
        Term term = new Term(request.title(), request.definition());
        return termService.add(term);
    }

    @GetMapping("/api/v1/terms/{query}")
    public AutocompleteKeywords query(@PathVariable String query) {
        return termService.query(query).stream()
                .map(Term::getTitle)
                .collect(collectingAndThen(toList(), AutocompleteKeywords::new));
    }
}
