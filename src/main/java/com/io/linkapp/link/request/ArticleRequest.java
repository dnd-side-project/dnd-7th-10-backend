package com.io.linkapp.link.request;

import com.io.linkapp.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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

    @Getter
    @NoArgsConstructor
    public static class DeleteTag {
        private UUID articleId;
        private UUID tagId;

        @Builder
        public DeleteTag(UUID articleId, UUID tagId) {
            this.articleId = articleId;
            this.tagId = tagId;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Modify {
        private UUID articleId;
        private UUID folderId;
        private List<UUID> tagIds = new ArrayList<>();

        @Builder
        public Modify(UUID articleId, UUID folderId, List<UUID> tagIds){
            this.articleId = articleId;
            this.folderId = folderId;
            this.tagIds = tagIds;
        }
    }


    @Getter
    @Builder
    public static class Search {
        private User user;
        private String title;
        private String description;
        private String tag;
    }
}
