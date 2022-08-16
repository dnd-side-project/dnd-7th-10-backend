package com.io.linkapp.config.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.io.linkapp.config.security.auth.PrincipalDetails;
import com.io.linkapp.user.domain.User;
import com.io.linkapp.user.service.RedisService;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//TODO: 삭제 예정
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private RedisService redisService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, RedisService redisService) {
        this.authenticationManager = authenticationManager;
        this.redisService = redisService;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(request.getInputStream(), User.class);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        String username = principalDetails.getUsername();

        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(redisService);

        String accessToken = jwtTokenProvider.generateAccessToken(username);
        String refreshToken = jwtTokenProvider.generateRefreshToken(username);

        JwtResponse jwtResponse = JwtResponse.builder()
                .header(JwtProperty.HEADER)
                .accessToken(JwtProperty.TOKEN_PREFIX + accessToken)
                .refreshToken(JwtProperty.TOKEN_PREFIX + refreshToken)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String jwtAccessToken = objectMapper.writeValueAsString(jwtResponse);
        response.getWriter().write(jwtAccessToken);
        response.addHeader(JwtProperty.HEADER, JwtProperty.TOKEN_PREFIX + accessToken);
    }
}
