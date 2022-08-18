package com.io.linkapp.link.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class ArticleRequest {

    private UUID folderId;
    private String linkUrl;
    private List<UUID> tagIds = new ArrayList<>();

    @Builder
    public ArticleRequest(UUID folderId, String linkUrl, List<UUID> tagIds) {
        this.folderId = folderId;
        this.linkUrl = linkUrl;
        this.tagIds = tagIds;
    }
}
