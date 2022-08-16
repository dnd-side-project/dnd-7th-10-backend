package com.io.linkapp.link.request;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FolderRequest {
    @NotBlank(message = "폴더명을 입력해주세요")
    private String folderTitle;

    private String folderColor;

    @Builder
    public FolderRequest(String folderTitle, String folderColor){
        this.folderTitle = folderTitle;
        this.folderColor = folderColor;
    }
}
