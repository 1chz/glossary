package app.zhc1.glossary.controller.request;

import app.zhc1.glossary.domain.Term;

public record TermUpdateRequest(int id, String title, String definition) {
    public Term toTerm() {
        return new Term(id, title, definition);
    }
}
