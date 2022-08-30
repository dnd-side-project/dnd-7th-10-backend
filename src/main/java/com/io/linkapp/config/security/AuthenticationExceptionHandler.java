package com.io.linkapp.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.io.linkapp.exception.ErrorResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthenticationExceptionHandler implements AuthenticationEntryPoint {

    @SneakyThrows
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException){
        log.error("AccessDenied Exception!!", authException);
        ErrorResponse error = ErrorResponse.customBuilder()
                .error("UnAuthorizedException")
                .status(401)
                .message("인증되지 않은 사용자입니다.")
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(error));
    }
}
