package app.zhc1.glossary.controller;

import app.zhc1.glossary.controller.response.SearchResultModel;
import app.zhc1.glossary.domain.Term;
import app.zhc1.glossary.service.TermService;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CommonPageController {
    private final TermService termService;

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/search-results")
    public String termSearchResultsPage(@RequestParam String keyword, Model model) {
        List<SearchResultModel> searchResults = termService.query(keyword).stream()
                .map(this::toModel)
                .sorted(Comparator.comparing(SearchResultModel::title))
                .toList();

        model.addAttribute("keyword", keyword);
        model.addAttribute("searchResults", searchResults);
        return "search-results";
    }

    @GetMapping("/terms/{id}")
    public String termDetailPage(@PathVariable int id, Model model) {
        SearchResultModel searchResult = termService
                .get(id)
                .map(this::toModel)
                .orElseThrow(() -> new IllegalArgumentException("Unknown term id: '%d'".formatted(id)));

        model.addAttribute("detail", searchResult);
        return "term-detail";
    }

    private SearchResultModel toModel(Term it) {
        return new SearchResultModel(
                it.getId(), it.getTitle(), it.getDefinition(), it.getCreatedAt(), it.getUpdatedAt());
    }
}
