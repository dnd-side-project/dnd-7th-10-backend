package com.io.linkapp.user.request;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Oauth2UserRequest {

    @NotBlank(message = "아이디를 입력해주세요.")
    private String username;
}
