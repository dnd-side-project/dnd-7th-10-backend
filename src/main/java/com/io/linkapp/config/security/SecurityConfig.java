package com.io.linkapp.config.security;

import com.io.linkapp.config.security.jwt.JwtAuthenticationFilter;
import com.io.linkapp.config.security.jwt.JwtAuthorizationFilter;
import com.io.linkapp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig {


    private final CorsFilter corsFilter;
    private final UserService userService;
    private final AuthenticationExceptionHandler jwtAuthenticationException;
    private final AuthenticationDeniedExceptionHandler jwtAccessDeniedException;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .formLogin().disable()
            .httpBasic().disable()
            .apply(new JwtCustomFilter())
            .and()
            .authorizeRequests()
            .antMatchers("/user/**")
            .permitAll()
            .anyRequest()
            .permitAll()
            .and()
            .oauth2Login()
            .defaultSuccessUrl("/")
            .and()
            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationException)
            .accessDeniedHandler(jwtAccessDeniedException)
            .and().build();
    }

    public class JwtCustomFilter extends AbstractHttpConfigurer<JwtCustomFilter, HttpSecurity> {

        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(
                AuthenticationManager.class);
            http.addFilter(corsFilter)
                .addFilter(new JwtAuthenticationFilter(authenticationManager))
                .addFilter(new JwtAuthorizationFilter(authenticationManager, userService));
        }
    }

}
