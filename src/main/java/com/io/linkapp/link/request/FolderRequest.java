package com.io.linkapp.link.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class FolderRequest {

    @NotBlank(message = "폴더명을 입력해주세요")
    private String folderTitle;
}
