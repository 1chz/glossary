package app.zhc1.glossary.controller.response;

import java.time.LocalDateTime;

public record SearchResultModel(
        int id, String title, String definition, LocalDateTime createdAt, LocalDateTime updatedAt) {}
