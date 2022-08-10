package com.io.linkapp.config.security.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.io.linkapp.config.security.auth.PrincipalDetails;
import com.io.linkapp.user.domain.User;
import com.io.linkapp.user.mapper.UserMapper;
import com.io.linkapp.user.request.Oauth2UserRequest;
import com.io.linkapp.user.request.UserRequest;
import com.io.linkapp.user.service.UserService;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PrincipalOauth2Service extends DefaultOAuth2UserService {

    private final UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("principalOauthService");
        OAuth2User oAuth2User = super.loadUser(userRequest);
        return processOauth2User(userRequest, oAuth2User);
    }

    private OAuth2User processOauth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        }

        User user;
        Optional<User> oauthUser = userService.findOauthUser(oAuth2UserInfo.getUsername());

        if (oauthUser.isPresent()) {
            user = oauthUser.get();
        } else {
            Oauth2UserRequest requestUser = Oauth2UserRequest.builder()
                .username(oAuth2UserInfo.getUsername())
                .build();
            user = UserMapper.INSTANCE.toEntity(requestUser);
            userService.oauth2Signup(requestUser);
        }
        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }
}
