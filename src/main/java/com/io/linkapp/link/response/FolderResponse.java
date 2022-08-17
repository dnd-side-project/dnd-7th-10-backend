package com.io.linkapp.link.response;

import com.io.linkapp.link.domain.Article;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class FolderResponse {

    private UUID folderId;
    private String folderTitle;
    private String folderColor;

    @Getter
    @Builder
    public static class GetAll{
        private UUID folderId;
        private String folderTitle;
        private String folderColor;
        private Integer articleCount;
    }

    @Getter
    @Builder
    public static class GetArticles{
        private UUID folderId;
        private String folderTitle;
        private String folderColor;
        private List<Article> articles;
    }
}
