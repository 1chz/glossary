package app.zhc1.glossary.service;

import app.zhc1.glossary.domain.Term;
import app.zhc1.glossary.domain.TermRevision;
import app.zhc1.glossary.repository.TermRepository;
import app.zhc1.glossary.repository.TermRevisionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TermService {
    private final EntityManager entityManager;
    private final TermRepository termRepository;
    private final TermRevisionRepository termRevisionRepository;

    @Transactional(readOnly = true)
    public Optional<Term> get(int id) {
        return termRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Page<Term> get(Pageable pageable) {
        return termRepository.findAll(pageable);
    }

    @Transactional
    public Term add(Term term) {
        if (!term.isNew()) {
            throw new IllegalArgumentException("Invalid add operation: %s".formatted(term));
        }

        term = termRepository.save(term);
        termRevisionRepository.save(new TermRevision(term));
        return term;
    }

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Term update(Term updatedTerm) {
        if (updatedTerm.isNew()) {
            throw new IllegalArgumentException("Invalid update operation: %s".formatted(updatedTerm));
        }

        var existingTerm = termRepository
                .findById(updatedTerm.getId())
                .orElseThrow(() -> new IllegalArgumentException("Term not found: %s".formatted(updatedTerm)));
        var revision = new TermRevision(existingTerm, updatedTerm.getTitle(), updatedTerm.getDefinition());

        existingTerm.updateTitle(updatedTerm.getTitle());
        existingTerm.updateDefinition(updatedTerm.getDefinition());

        termRevisionRepository.save(revision);
        return existingTerm;
    }

    @Transactional(readOnly = true)
    public List<Term> query(String keyword) {
        return Search.session(entityManager)
                .search(Term.class)
                .where(f -> f.match()
                        .fields("title", "definition")
                        .matching(keyword)
                        .fuzzy(this.calculateFuzzyDistance(keyword)))
                .fetchAllHits();
    }

    @Transactional(readOnly = true)
    public Page<Term> query(String keyword, Pageable pageable) {
        SearchResult<Term> results = Search.session(entityManager)
                .search(Term.class)
                .where(f -> f.match()
                        .fields("title", "definition")
                        .matching(keyword)
                        .fuzzy(this.calculateFuzzyDistance(keyword)))
                .fetch((int) pageable.getOffset(), pageable.getPageSize());

        return new PageImpl<>(results.hits(), pageable, results.total().hitCount());
    }

    private int calculateFuzzyDistance(String query) {
        boolean shortQuery = query.length() < 4;

        // 1: Up to one letter difference is allowed
        // 2: Up to two letter difference is allowed
        return shortQuery ? 1 : 2;
    }
}
