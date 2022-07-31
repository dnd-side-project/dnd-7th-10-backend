package com.io.linkapp.link.response;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemoResponse {
    private UUID id;
    private UUID articleId;
    private String content;
    private LocalDateTime registerDate;
    private LocalDateTime modifiedDate;

    @Builder
    public MemoResponse(UUID id, UUID articleId, String content, LocalDateTime registerDate,
        LocalDateTime modifiedDate) {
        this.id = id;
        this.articleId = articleId;
        this.content = content;
        this.registerDate = registerDate;
        this.modifiedDate = modifiedDate;
    }
}
