package app.zhc1.glossary.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Indexed
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Term {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @FullTextField(analyzer = "standard")
    @Column(nullable = false, length = 50)
    private String title;

    @FullTextField(analyzer = "standard")
    @Column(nullable = false, length = 5_000)
    private String definition;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Term(String title, String definition) {
        this(null, title, definition);
    }

    public Term(Integer id, String title, String definition) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title must not be null or blank: %s".formatted(title));
        }
        if (definition == null || definition.isBlank()) {
            throw new IllegalArgumentException("definition must not be null or blank: %s".formatted(definition));
        }
        if (definition.length() > 5_000) {
            throw new IllegalArgumentException(
                    "definition must not exceed 5,000 characters: %d".formatted(definition.length()));
        }

        this.id = id;
        this.title = title;
        this.definition = definition;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isNew() {
        return id == null;
    }

    public void updateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title must not be null or blank: %s".formatted(title));
        }
        if (title.length() > 50) {
            throw new IllegalArgumentException("title must not exceed 50 characters: %d".formatted(title.length()));
        }
        this.title = title;
    }

    public void updateDefinition(String definition) {
        if (definition == null || definition.isBlank()) {
            throw new IllegalArgumentException("definition must not be null or blank: %s".formatted(definition));
        }
        if (definition.length() > 5_000) {
            throw new IllegalArgumentException(
                    "definition must not exceed 5,000 characters: %d".formatted(definition.length()));
        }
        this.definition = definition;
    }

    @Override
    public String toString() {
        return "Term{id='%d', term='%s', createdAt='%s', updatedAt='%s'}".formatted(id, title, createdAt, updatedAt);
    }

    @Override
    public final boolean equals(Object o) {
        return this == o || o instanceof Term && Objects.equals(id, ((Term) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
