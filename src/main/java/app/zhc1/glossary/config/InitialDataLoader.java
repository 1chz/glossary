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
        termService.add(new Term(
                "Spring", "An application framework and inversion of control container for the Java platform."));
        termService.add(new Term("Hibernate", "A state of enforced isolation."));
        termService.add(
                new Term(
                        "JPA",
                        """
                <h1>JPA (Java Persistence API)</h1><p>JPA는 자바 애플리케이션에서 관계형 데이터베이스를 사용하는 방식을 정의한 자바 ORM 기술의 표준 사양(명세)입니다.</p><p><br></p><h2>핵심 개념</h2><h3>1. ORM (Object-Relational Mapping)</h3><ul><li>객체와 관계형 데이터베이스를 매핑하는 기술</li><li>객체지향 프로그래밍과 관계형 데이터베이스 간의 패러다임 불일치 해결</li></ul><p><br></p><pre class="ql-syntax" spellcheck="false">@Entity
                public class Member {
                 @Id @GeneratedValue
                 private Long id;

                 @Column(name = "username")
                 private String name;

                 @ManyToOne
                 @JoinColumn(name = "team_id")
                 private Team team;
                }

                </pre><p><br></p>
                """));

        log.info("Sample terms added !");
    }
}
