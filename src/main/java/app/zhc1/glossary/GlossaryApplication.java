package app.zhc1.glossary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GlossaryApplication {
    public static void main(String[] args) {
        SpringApplication.run(GlossaryApplication.class, args);
    }
}
