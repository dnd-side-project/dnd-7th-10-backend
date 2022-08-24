package com.io.linkapp.user.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class RefreshRequest {
    private String accessToken;
    private String refreshToken;
}
