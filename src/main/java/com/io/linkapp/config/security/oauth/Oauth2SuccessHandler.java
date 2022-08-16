package com.io.linkapp.config.security.oauth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.io.linkapp.config.security.auth.PrincipalDetails;
import com.io.linkapp.config.security.jwt.JwtProperty;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class Oauth2SuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws ServletException, IOException {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        String jwtToken = JWT.create()
            .withSubject(JwtProperty.SUBJECT)
            .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperty.EXPIRATION_TIME))
            .withClaim("username", principalDetails.getUsername())
            .withClaim("role", principalDetails.getUser().getRole().toString())
            .sign(Algorithm.HMAC512(JwtProperty.SECRET));

        response.addHeader(JwtProperty.HEADER, JwtProperty.TOKEN_PREFIX + jwtToken);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
