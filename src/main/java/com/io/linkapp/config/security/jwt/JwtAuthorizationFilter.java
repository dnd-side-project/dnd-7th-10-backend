package com.io.linkapp.config.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.io.linkapp.config.security.auth.PrincipalDetails;
import com.io.linkapp.exception.ErrorResponse;
import com.io.linkapp.exception.RefreshTokenNotFoundException;
import com.io.linkapp.exception.RefreshTokenNotValidateException;
import com.io.linkapp.user.domain.User;
import com.io.linkapp.user.service.RedisService;
import com.io.linkapp.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.ObjectUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserService userService;
    private RedisService redisService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserService userService, RedisService redisService) {
        super(authenticationManager);
        this.userService = userService;
        this.redisService = redisService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!ObjectUtils.isEmpty(request.getHeader(JwtProperty.HEADER))) {
            String jwtToken = request.getHeader(JwtProperty.HEADER).replace(JwtProperty.TOKEN_PREFIX, "");
            JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(redisService);
            if(jwtTokenProvider.isTokenExpired(jwtToken)){
                try {

                    String accessToken = jwtTokenProvider.findRefreshToken(request)
                            .validateRefreshToken()
                            .regenerateAccessToken();

                    response.addHeader(JwtProperty.HEADER, JwtProperty.TOKEN_PREFIX + accessToken);

                    JwtResponse responseAccessToken = JwtResponse.builder()
                            .header(JwtProperty.HEADER)
                            .accessToken(JwtProperty.TOKEN_PREFIX + accessToken)
                            .build();

                    ObjectMapper objectMapper = new ObjectMapper();
                    response.getWriter().write(objectMapper.writeValueAsString(responseAccessToken));
                    return;

                } catch (RefreshTokenNotFoundException e){
                    log.error("RefreshToken Error!", e);
                    ErrorResponse error = ErrorResponse.customBuilder()
                            .error("RefreshTokenNotFoundException")
                            .status(404)
                            .message("Can not Found Refresh Token.")
                            .build();
                    ObjectMapper objectMapper = new ObjectMapper();
                    response.getWriter().write(objectMapper.writeValueAsString(error));
                    return;
                } catch (RefreshTokenNotValidateException e){
                    log.error("RefreshToken Error!", e);
                    ErrorResponse error = ErrorResponse.customBuilder()
                            .error("RefreshTokenNotValidatedException")
                            .status(400)
                            .message("Refresh Token is Not Validated.")
                            .build();
                    ObjectMapper objectMapper = new ObjectMapper();
                    response.getWriter().write(objectMapper.writeValueAsString(error));
                    return;
                }
            } else{
                String username = jwtTokenProvider.getUsername(jwtToken);
                User user = userService.findByUsername(username);
                PrincipalDetails principalDetails = new PrincipalDetails(user);
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        principalDetails, null, principalDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                String redisRefreshToken = redisService.getValues(username);
                if(redisRefreshToken != null) {
                    redisService.setValues(username, redisRefreshToken);
                }
            }
        }
        chain.doFilter(request, response);
    }
}
