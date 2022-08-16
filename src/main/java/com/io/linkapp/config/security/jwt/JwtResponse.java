package com.io.linkapp.config.security.jwt;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtResponse {
    private String header;
    private String accessToken;
    private String refreshToken;
}
