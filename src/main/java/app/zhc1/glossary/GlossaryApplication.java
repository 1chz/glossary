package app.zhc1.glossary;

import app.zhc1.glossary.domain.Term;
import app.zhc1.glossary.service.TermService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Slf4j
@EnableJpaAuditing
@SpringBootApplication
@RequiredArgsConstructor
public class GlossaryApplication implements CommandLineRunner {

    @Value("${glossary.dbpath}")
    private String dbPath;

    private final TermService termService;

    public static void main(String[] args) {
        SpringApplication.run(GlossaryApplication.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("Glossary DB Path: {}", dbPath);

        log.info("Adding sample terms...");
        termService.add(new Term("Java", "A high-level programming language developed by Sun Microsystems."));
        termService.add(new Term("Java", "An island of Indonesia."));
        termService.add(new Term(
                "Spring", "An application framework and inversion of control container for the Java platform."));
        termService.add(new Term("Spring", "A resilient plant of the daisy family, with yellow or white flowers."));
        termService.add(new Term("Hibernate", "An object-relational mapping tool for the Java programming language."));
        termService.add(new Term("Hibernate", "A state of enforced isolation."));
        termService.add(new Term("JPA", "Java Persistence API."));
        termService.add(new Term("JPA", "Joint Photographic Experts Group."));
        termService.add(new Term("JPA", "Japan Press Association."));
        termService.add(new Term("JPA", "Jewish Partisan Association."));
        log.info("Sample terms added !");
    }
}
