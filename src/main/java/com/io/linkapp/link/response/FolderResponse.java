package com.io.linkapp.link.response;

import lombok.Builder;
import lombok.Getter;

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
}
