package com.io.linkapp.config.sercurity;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.io.linkapp.user.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.ObjectUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserService userService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserService userService) {
        super(authenticationManager);
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("JwtAuthorizationFilter, doFilterInternal");

        if(!ObjectUtils.isEmpty(request.getHeader(JwtProperty.HEADER))) {
            System.out.println("으으음...");
            String jwtToken = request.getHeader(JwtProperty.HEADER).replace(JwtProperty.TOKEN_PREFIX, "");
            System.out.println(jwtToken);
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC512(JwtProperty.SECRET)).build();

            DecodedJWT decodedJWT = jwtVerifier.verify(jwtToken);
            System.out.println(decodedJWT);
        }

        chain.doFilter(request, response);
    }
}
