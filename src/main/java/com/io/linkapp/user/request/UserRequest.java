package com.io.linkapp.user.request;

import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserRequest {
    private Long id;
    
    @NotBlank(message = "아이디를 입력해주세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    public void encodePassword(String password) {
        this.password = password;
    }
}
