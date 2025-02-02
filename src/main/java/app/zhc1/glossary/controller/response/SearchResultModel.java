package app.zhc1.glossary.controller.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public final class SearchResultModel {
    private final int id;
    private final String title;
    private final String definition;
    private final String shortDefinition;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public SearchResultModel(
            int id, String title, String definition, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.definition = definition;
        this.shortDefinition = removeHtmlTags(definition);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    private String removeHtmlTags(String definition) {
        String plainText =
                definition.replaceAll("<[^>]*>", "").replaceAll("\\s+", " ").trim();

        return plainText.length() > 50 ? plainText.substring(0, 50) + "..." : plainText;
    }
}
