package com.io.linkapp.link.request;

import com.io.linkapp.common.BaseTimeEntity;
import com.io.linkapp.link.domain.Folder;
import com.io.linkapp.user.domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ArticleRequest extends BaseTimeEntity {

    private UUID folderId;
    private String linkTitle;
    private String linkContent;
    private List<UUID> tags = new ArrayList<>();

    @Builder
    public ArticleRequest(UUID folderId, String linkTitle, String linkContent, List<UUID> tags) {
        this.folderId = folderId;
        this.linkTitle = linkTitle;
        this.linkContent = linkContent;
        this.tags = tags;
    }
}
