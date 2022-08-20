package com.io.linkapp.link.response;

import com.io.linkapp.link.domain.OpenGraph;
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
    private OpenGraph openGraph;
    private String folderTitle;

    @Builder
    public MemoResponse(UUID id, String content, LocalDateTime registerDate,
        LocalDateTime modifiedDate, OpenGraph openGraph, String folderTitle) {
        this.id = id;
        this.content = content;
        this.registerDate = registerDate;
        this.modifiedDate = modifiedDate;
        this.openGraph = openGraph;
        this.folderTitle = folderTitle;
    }
}
