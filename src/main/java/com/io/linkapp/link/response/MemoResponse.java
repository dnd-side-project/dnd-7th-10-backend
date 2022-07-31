package com.io.linkapp.link.response;

import com.io.linkapp.link.domain.Article;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemoResponse {
    private UUID id;
    private Article article;
    private String content;
    private LocalDateTime registerDate;
    private LocalDateTime modifiedDate;

    @Builder
    public MemoResponse(UUID id, Article article, String content, LocalDateTime registerDate,
        LocalDateTime modifiedDate) {
        this.id = id;
        this.article = article;
        this.content = content;
        this.registerDate = registerDate;
        this.modifiedDate = modifiedDate;
    }
}
