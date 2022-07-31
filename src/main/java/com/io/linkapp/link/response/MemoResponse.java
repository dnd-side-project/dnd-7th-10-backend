package com.io.linkapp.link.response;

import com.io.linkapp.link.domain.Memo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class MemoResponse {
    private UUID id;
    private UUID articleId;
    private String content;
    private LocalDateTime registerDate;
    private LocalDateTime modifiedDate;

    public MemoResponse(Memo memo) {
        this.id = memo.getId();
        this.articleId = memo.getArticleId();
        this.content = memo.getContent();
        this.registerDate = memo.getRegisterDate();
        this.modifiedDate = memo.getModifiedDate();
    }

    @Builder
    public MemoResponse(UUID id, UUID articleId, String content, LocalDateTime registerDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.articleId = articleId;
        this.content = content;
        this.registerDate = registerDate;
        this.modifiedDate = modifiedDate;
    }
}
