package com.io.linkapp.link.request;

import com.io.linkapp.common.BaseTimeEntity;
import com.io.linkapp.link.domain.Folder;
import com.io.linkapp.user.domain.User;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ArticleRequest extends BaseTimeEntity {

    private UUID folderId;
    private String linkTitle;
    private String linkContent;

    @Builder
    public ArticleRequest(UUID folderId, String linkTitle, String linkContent) {
        this.folderId = folderId;
        this.linkTitle = linkTitle;
        this.linkContent = linkContent;
    }
}
