package com.io.linkapp.link.request;

import com.io.linkapp.common.BaseTimeEntity;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ArticleRequest extends BaseTimeEntity {

    private UUID userId;
    private UUID folderId;
    private UUID remindId;

    private String linkTitle;
    private String linkContent;

    @Builder
    public ArticleRequest(UUID userId, UUID folderId, UUID remindId, String linkTitle,
        String linkContent) {
        this.userId = userId;
        this.folderId = folderId;
        this.remindId = remindId;
        this.linkTitle = linkTitle;
        this.linkContent = linkContent;
    }

}
