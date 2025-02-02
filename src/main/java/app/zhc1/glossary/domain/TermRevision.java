package app.zhc1.glossary.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class TermRevision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer version;

    @ManyToOne
    private Term ref;

    @Column(nullable = true, length = 50)
    private String beforeTitle;

    @Column(nullable = false, length = 50)
    private String afterTitle;

    @Column(nullable = true, length = 5_000)
    private String beforeDefinition;

    @Column(nullable = false, length = 5_000)
    private String afterDefinition;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public TermRevision(Term newTerm) {
        this.version = null;
        this.ref = newTerm;
        this.beforeTitle = null;
        this.afterTitle = newTerm.getTitle();
        this.beforeDefinition = null;
        this.afterDefinition = newTerm.getDefinition();
        this.createdAt = LocalDateTime.now();
    }

    public TermRevision(Term before, String afterTitle, String afterDefinition) {
        this.version = null;
        this.ref = before;
        this.beforeTitle = before.getTitle();
        this.afterTitle = afterTitle;
        this.beforeDefinition = before.getDefinition();
        this.afterDefinition = afterDefinition;
        this.createdAt = LocalDateTime.now();
    }
}
