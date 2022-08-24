package com.io.linkapp.config.security.jwt;

import com.io.linkapp.config.security.auth.PrincipalDetails;
import com.io.linkapp.user.domain.User;
import com.io.linkapp.user.service.RedisService;
import com.io.linkapp.user.service.UserService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.ObjectUtils;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserService userService;
    private RedisService redisService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
        UserService userService, RedisService redisService) {
        super(authenticationManager);
        this.userService = userService;
        this.redisService = redisService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain chain) throws IOException, ServletException {
        if (!ObjectUtils.isEmpty(request.getHeader(JwtProperty.HEADER))) {
            String jwtToken = request.getHeader(JwtProperty.HEADER)
                .replace(JwtProperty.TOKEN_PREFIX, "");
            JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(redisService);
            if (jwtTokenProvider.isTokenExpired(jwtToken)) {
                response.sendError(401);
                return;
            }
            String username = jwtTokenProvider.getUsername(jwtToken);
            User user = userService.findByUsername(username);
            PrincipalDetails principalDetails = new PrincipalDetails(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                principalDetails, null, principalDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String redisRefreshToken = redisService.getValues(username);
            if (redisRefreshToken != null) {
                redisService.setValues(username, redisRefreshToken);
            }
        }
        chain.doFilter(request, response);
    }
}
