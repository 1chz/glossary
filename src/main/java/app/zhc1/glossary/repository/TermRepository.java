package app.zhc1.glossary.repository;

import app.zhc1.glossary.domain.Term;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermRepository extends JpaRepository<Term, Integer> {}
