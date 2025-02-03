package app.zhc1.glossary.controller.request;

import app.zhc1.glossary.domain.Term;

public record TermCreateRequest(String title, String definition) {
    public Term toTerm() {
        return new Term(title, definition);
    }
}
