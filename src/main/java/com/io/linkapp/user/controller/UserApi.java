package com.io.linkapp.user.controller;

import com.io.linkapp.config.security.auth.PrincipalDetails;
import com.io.linkapp.config.security.jwt.JwtResponse;
import com.io.linkapp.config.security.jwt.JwtTokenProvider;
import com.io.linkapp.link.request.KakaoRequest;
import com.io.linkapp.link.response.SuccessResponse;
import com.io.linkapp.user.domain.User;
import com.io.linkapp.user.request.RefreshRequest;
import com.io.linkapp.user.request.UserRequest;
import com.io.linkapp.user.response.UserResponse;
import com.io.linkapp.user.service.RedisService;
import com.io.linkapp.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(value = "User", tags = {"User"})
@RequiredArgsConstructor
@RestController
public class UserApi {

    private final UserService userService;
    private final RedisService redisService;

    @ApiOperation("회원 가입")
    @PostMapping("/user")
    public User join(@RequestBody @Valid UserRequest userRequest) {
        return userService.join(userRequest);
    }

    @ApiOperation("회원 찾기")
    @GetMapping("/user/{userEmail}")
    public UserResponse getUser(@PathVariable("userEmail") String userEmail) {
        return userService.findUser(userEmail);
    }

    @ApiOperation("회원 전체 조회")
    @GetMapping("/users")
    public List<UserResponse> getUsers() {
        return userService.findAll();
    }

    @ApiOperation("회원 탈퇴")
    @DeleteMapping("/user")
    public SuccessResponse removeUser(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        User user = principalDetails.getUser();
        return userService.removeUser(user);
    }

    @ApiOperation(value = "카카오 로그인", notes = "첫 요청 시 회원가입 후 로그인, 이후 요청은 로그인")
    @PostMapping("/kakao")
    public JwtResponse kakaoLogin(@RequestBody KakaoRequest kakaoRequest) {
        return userService.kakaoLogin(kakaoRequest);
    }

    @ApiOperation(value = "리프레시 토큰")
    @PostMapping("/refresh")
    public JwtResponse regenerateAccessToken(@RequestBody RefreshRequest refreshRequest) {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(redisService);
        String accessToken = jwtTokenProvider.findRefreshToken(refreshRequest)
            .validateRefreshToken()
            .regenerateAccessToken();

        return JwtResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshRequest.getRefreshToken())
            .build();
    }
}
