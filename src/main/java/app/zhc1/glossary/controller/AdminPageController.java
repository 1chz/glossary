package app.zhc1.glossary.controller;

import app.zhc1.glossary.controller.response.SearchResultModel;
import app.zhc1.glossary.service.TermService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AdminPageController {
    private final TermService termService;

    @GetMapping("/admin")
    public String adminPage(
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            Model model) {
        Page<SearchResultModel> terms;
        if (StringUtils.hasText(keyword)) {
            terms = termService
                    .query(keyword, pageable)
                    .map(term -> new SearchResultModel(
                            term.getId(),
                            term.getTitle(),
                            term.getDefinition(),
                            term.getCreatedAt(),
                            term.getUpdatedAt()));
        } else {
            terms = termService
                    .getAllTerms(pageable)
                    .map(term -> new SearchResultModel(
                            term.getId(),
                            term.getTitle(),
                            term.getDefinition(),
                            term.getCreatedAt(),
                            term.getUpdatedAt()));
        }

        model.addAttribute("keyword", keyword);
        model.addAttribute("terms", terms);
        model.addAttribute("currentPage", terms.getNumber());
        model.addAttribute("totalPages", terms.getTotalPages());
        model.addAttribute("totalItems", terms.getTotalElements());

        return "admin/index";
    }

    @GetMapping("/admin/terms/new")
    public String newTermForm() {
        return "admin/term-form";
    }

    @GetMapping("/admin/terms/{id}/edit")
    public String editTermForm(@PathVariable int id, Model model) {
        SearchResultModel term = termService
                .get(id)
                .map(t -> new SearchResultModel(
                        t.getId(), t.getTitle(), t.getDefinition(), t.getCreatedAt(), t.getUpdatedAt()))
                .orElseThrow(() -> new IllegalArgumentException("Unknown term id: " + id));

        model.addAttribute("term", term);
        return "admin/term-form";
    }
}
