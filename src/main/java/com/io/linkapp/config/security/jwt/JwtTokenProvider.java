package com.io.linkapp.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.io.linkapp.exception.CustomGlobalException;
import com.io.linkapp.exception.ErrorCode;
import com.io.linkapp.user.request.RefreshRequest;
import com.io.linkapp.user.service.RedisService;
import java.util.Date;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Getter
public class JwtTokenProvider {

    private boolean isValidate;
    private String username;
    private String refreshToken;
    private String jwtToken;

    private final RedisService redisService;

    public String generateAccessToken(String username){
    return JWT.create()
            .withSubject(JwtProperty.SUBJECT)
            .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperty.EXPIRATION_TIME))
            .withClaim("username", username)
            .sign(Algorithm.HMAC512(JwtProperty.SECRET));
    }

    public String generateRefreshToken(String username) {
        String refreshToken = JWT.create()
                .sign(Algorithm.HMAC512(JwtProperty.SECRET));
        redisService.setValues(username, refreshToken);
        return refreshToken;
    }

    public String getUsername(String jwtToken) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC512(JwtProperty.SECRET)).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(jwtToken);
        return decodedJWT.getClaim("username").asString();
    }

    public boolean isTokenExpired(String jwtToken) {
        Date expiresAt = JWT.decode(jwtToken).getExpiresAt();
        Date now = new Date(System.currentTimeMillis());
        this.jwtToken = jwtToken;
        if(now.after(expiresAt)){
            return true;
        } else {
            return false;
        }
    }

    @SneakyThrows
    public JwtTokenProvider findRefreshToken(RefreshRequest refreshRequest){
        this.refreshToken = refreshRequest.getRefreshToken();
        this.jwtToken = refreshRequest.getAccessToken();
        return this;
    }

    public JwtTokenProvider validateRefreshToken() {
        String username;
        try {
            username = JWT.decode(this.jwtToken)
                .getClaim("username")
                .asString();
        } catch (RuntimeException e) {
            throw new CustomGlobalException(ErrorCode.TOKEN_NOT_VALID);
        }
        this.username = username;

        if(refreshToken.equals(redisService.getValues(username))){
            this.isValidate = true;
        }else{
            throw new CustomGlobalException(ErrorCode.REFRESH_NOT_VALID);
        }
        return this;
    }

    public String regenerateAccessToken(){
        return generateAccessToken(this.username);
    }

    public JwtResponse provideToken(String username){
        String accessToken = this.generateAccessToken(username);
        String refreshToken = this.generateRefreshToken(username);

        return JwtResponse.builder()
            .header(JwtProperty.HEADER)
            .accessToken(JwtProperty.TOKEN_PREFIX + accessToken)
            .refreshToken(JwtProperty.TOKEN_PREFIX + refreshToken)
            .build();
    }
}
