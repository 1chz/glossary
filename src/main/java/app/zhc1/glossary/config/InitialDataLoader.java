package app.zhc1.glossary.config;

import app.zhc1.glossary.domain.Term;
import app.zhc1.glossary.domain.User;
import app.zhc1.glossary.domain.UserRole;
import app.zhc1.glossary.repository.UserRepository;
import app.zhc1.glossary.service.TermService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitialDataLoader implements CommandLineRunner {
    private final TermService termService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        setupAdmins();
        setupSampleTerms();
    }

    private void setupAdmins() {
        if (!userRepository.existsByUsername("admin")) {
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("1234"))
                    .name("관리자")
                    .email("admin@example.com")
                    .roles(Set.of(UserRole.ROLE_ADMIN, UserRole.ROLE_USER))
                    .build();

            userRepository.save(admin);
        }
    }

    private void setupSampleTerms() {
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
