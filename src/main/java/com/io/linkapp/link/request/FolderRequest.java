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

    @Builder
    public FolderRequest(String folderTitle){
        this.folderTitle = folderTitle;
    }
}
