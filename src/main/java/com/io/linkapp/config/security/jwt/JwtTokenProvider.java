package com.io.linkapp.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.io.linkapp.exception.RefreshTokenNotFoundException;
import com.io.linkapp.exception.RefreshTokenNotValidateException;
import com.io.linkapp.user.service.RedisService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperty.REFRESH_TOKEN_EXPIRATION))
                .sign(Algorithm.HMAC512(JwtProperty.SECRET));

        log.info("Set Redis");
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
        if(now.after(expiresAt)){
            return true;
        } else {
            return false;
        }
    }

    public JwtTokenProvider findRefreshToken(HttpServletRequest request){
        boolean empty = ObjectUtils.isEmpty(request.getHeader(JwtProperty.REFRESH_HEADER));
        if(empty) {
            throw new RefreshTokenNotFoundException();
        } else {
            this.refreshToken = request.getHeader(JwtProperty.REFRESH_HEADER).replace(JwtProperty.TOKEN_PREFIX, "");
        }
        return this;
    }

    public JwtTokenProvider validateRefreshToken() {
        String username = JWT.decode(this.jwtToken).getClaim("username")
                .asString();

        this.username = username;

        if(refreshToken.equals(redisService.getValues(username))){
            this.isValidate = true;
        }else{
            throw new RefreshTokenNotValidateException();
        }

        return this;
    }

    public String regenerateAccessToken(){
        return generateAccessToken(this.username);
    }
}
