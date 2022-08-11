package com.io.linkapp.link.request;

import com.io.linkapp.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class FolderRequest {

    private User user;

    @NotBlank(message = "폴더명을 입력해주세요")
    private String folderTitle;

    public void setUser(User user){
        this.user = user;
    }
}
