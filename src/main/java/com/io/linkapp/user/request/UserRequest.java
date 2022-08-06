package com.io.linkapp.user.request;

import lombok.Getter;

@Getter
public class UserRequest {
    private String username;
    private String password;

    public void encodePassword(String password) {
        this.password = password;
    }
}
