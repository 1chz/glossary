package app.zhc1.glossary.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TermIndexingService {
    private final EntityManager entityManager;

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void initializeHibernateSearch() {
        log.info("In indexing terms...");

        try {
            SearchSession searchSession = Search.session(entityManager);
            searchSession.massIndexer().startAndWait();
            log.info("Completed indexing!");

        } catch (InterruptedException e) {
            log.error("Failed to index: {}", e.getMessage());
        }
    }
}
