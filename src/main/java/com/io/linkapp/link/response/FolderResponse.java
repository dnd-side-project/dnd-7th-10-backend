package com.io.linkapp.link.response;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class FolderResponse {

    private UUID folderId;
    private String folderTitle;
}
