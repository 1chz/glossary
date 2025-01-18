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
@Indexed
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Term {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @FullTextField(analyzer = "standard")
    @Column(nullable = false, length = 50)
    private String title;

    @Getter
    @FullTextField(analyzer = "standard")
    @Column(nullable = false, length = 2_000)
    private String definition;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Term(String title, String definition) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title must not be null or blank: %s".formatted(title));
        }
        if (definition == null || definition.isBlank()) {
            throw new IllegalArgumentException("definition must not be null or blank: %s".formatted(definition));
        }

        this.id = null;
        this.title = title;
        this.definition = definition;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isNew() {
        return id == null;
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
