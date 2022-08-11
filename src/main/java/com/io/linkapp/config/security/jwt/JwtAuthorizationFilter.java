package com.io.linkapp.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.io.linkapp.config.security.auth.PrincipalDetails;
import com.io.linkapp.user.domain.User;
import com.io.linkapp.user.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.util.ObjectUtils;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserService userService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserService userService) {
        super(authenticationManager);
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (!ObjectUtils.isEmpty(request.getHeader(JwtProperty.HEADER))) {
            String jwtToken = request.getHeader(JwtProperty.HEADER).replace(JwtProperty.TOKEN_PREFIX, "");
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC512(JwtProperty.SECRET)).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(jwtToken);
            String username = decodedJWT.getClaim("username").asString();

            if(username != null) {
                User user = userService.findByUsername(username);
                PrincipalDetails principalDetails = new PrincipalDetails(user);
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                principalDetails, null, principalDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }
}
