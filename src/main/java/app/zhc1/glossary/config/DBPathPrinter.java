package app.zhc1.glossary.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DBPathPrinter implements CommandLineRunner {
    private final String dbPath;

    public DBPathPrinter(@Value("${glossary.dbpath}") String dbPath) {
        this.dbPath = validatePath(dbPath);
    }

    private String validatePath(String path) {
        if (path == null || path.trim().isEmpty()) {
            throw new IllegalArgumentException("DB path cannot be empty !");
        }
        return path;
    }

    @Override
    public void run(String... args) {
        log.info("Glossary DB Path: {}/.glossary/db", dbPath);
    }
}
