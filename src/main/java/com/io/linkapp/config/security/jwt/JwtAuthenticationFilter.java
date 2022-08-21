package com.io.linkapp.config.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.io.linkapp.config.security.auth.PrincipalDetails;
import com.io.linkapp.exception.ErrorResponse;
import com.io.linkapp.user.domain.User;
import com.io.linkapp.user.service.RedisService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
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
@Slf4j
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
        try {
            return authenticationManager.authenticate(authenticationToken);
        } catch (InternalAuthenticationServiceException e) {
            log.error("UserNotFoundException!", e);
            ErrorResponse error = ErrorResponse.customBuilder()
                    .error("UserNotFoundException")
                    .status(404)
                    .message("Can not Found User.")
                    .build();
            response.getWriter().write(objectMapper.writeValueAsString(error));
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
            PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
            String username = principalDetails.getUsername();

            JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(redisService);
        JwtResponse jwtResponse = jwtTokenProvider.provideToken(username);

        ObjectMapper objectMapper = new ObjectMapper();
            String jwtAccessToken = objectMapper.writeValueAsString(jwtResponse);
            response.getWriter().write(jwtAccessToken);
            response.addHeader(JwtProperty.HEADER, JwtProperty.TOKEN_PREFIX + jwtResponse.getAccessToken());
        }
}
