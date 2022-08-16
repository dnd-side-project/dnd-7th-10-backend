package com.io.linkapp.link.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class MemoResponse {
    private UUID id;
    private String content;
    private LocalDateTime registerDate;
    private LocalDateTime modifiedDate;

    @Builder
    public MemoResponse(UUID id, String content, LocalDateTime registerDate,
        LocalDateTime modifiedDate) {
        this.id = id;
        this.content = content;
        this.registerDate = registerDate;
        this.modifiedDate = modifiedDate;
    }
}
