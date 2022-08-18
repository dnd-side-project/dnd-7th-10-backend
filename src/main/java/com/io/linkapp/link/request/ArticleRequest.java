package com.io.linkapp.link.request;

import com.io.linkapp.common.BaseTimeEntity;
import com.io.linkapp.link.domain.Folder;
import com.io.linkapp.link.domain.Tag;
import com.io.linkapp.user.domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArticleRequest extends BaseTimeEntity {

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
