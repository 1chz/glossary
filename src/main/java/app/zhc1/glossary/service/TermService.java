package app.zhc1.glossary.service;

import app.zhc1.glossary.domain.Term;
import app.zhc1.glossary.domain.TermRevision;
import app.zhc1.glossary.repository.TermRepository;
import app.zhc1.glossary.repository.TermRevisionRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hibernate.search.mapper.orm.Search;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TermService {
    private final EntityManager entityManager;
    private final TermRepository termRepository;
    private final TermRevisionRepository termRevisionRepository;

    @Transactional
    public Term add(Term term) {
        if (!term.isNew()) {
            throw new IllegalArgumentException("Invalid add operation: %s".formatted(term));
        }

        Term newTerm = termRepository.save(term);
        termRevisionRepository.save(new TermRevision(newTerm));
        return newTerm;
    }

    public void delete(Integer id) {
        // TODO: Implement this method
    }

    @Transactional
    public Term update(Term term) {
        if (term.isNew()) {
            throw new IllegalArgumentException("Invalid update operation: %s".formatted(term));
        }

        Term oldTerm = termRepository
                .findById(term.getId())
                .orElseThrow(() -> new IllegalArgumentException("Term not found: %s".formatted(term)));
        Term newTerm = termRepository.save(term);
        termRevisionRepository.save(new TermRevision(oldTerm, newTerm));
        return newTerm;
    }

    @Transactional(readOnly = true)
    public List<Term> query(String query) {
        return Search.session(entityManager)
                .search(Term.class)
                .where(f ->
                        f.match().fields("title", "definition").matching(query).fuzzy(calculateFuzzyDistance(query)))
                .fetchHits(10);
    }

    private int calculateFuzzyDistance(String query) {
        boolean shortQuery = query.length() < 4;

        // 1: Up to one letter difference is allowed
        // 2: Up to two letter difference is allowed
        return shortQuery ? 1 : 2;
    }
}
