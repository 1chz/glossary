package app.zhc1.glossary.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DBPathPrinter implements CommandLineRunner {
    @Value("${glossary.dbpath}")
    private String dbPath;

    @Override
    public void run(String... args) {
        log.info("Glossary DB Path: {}/.glossary/db", dbPath);
    }
}
