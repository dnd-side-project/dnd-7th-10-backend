package com.io.linkapp.user.controller;

import com.io.linkapp.config.security.jwt.JwtResponse;
import com.io.linkapp.link.request.KakaoRequest;
import com.io.linkapp.user.domain.User;
import com.io.linkapp.user.request.UserRequest;
import com.io.linkapp.user.response.UserResponse;
import com.io.linkapp.user.service.RedisService;
import com.io.linkapp.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/user/{username}")
    public UserResponse getUser(@PathVariable("username") String username){
        return userService.findUser(username);
    }

    @ApiOperation("회원 전체 조회")
    @GetMapping("/users")
    public List<UserResponse> getUsers(){
        return userService.findAll();
    }

    @ApiOperation(value = "로그인", notes = "로그인 시 실제 요청은 Spring Security 가 수행함. 클라이언트 확인을 위한 API")
    @PostMapping("/kakao")
    public JwtResponse kakaoLogin(@RequestBody KakaoRequest kakaoRequest) {
        return userService.kakaoLogin(kakaoRequest);
    }

    @ApiOperation(value = "카카오 로그인 주소", notes = "동작하지 않음. 요청 주소 확인을 위한 API")
    @GetMapping("/oauth2/authorization/kakao")
    public void kakaoLoginDummyApi(){
    }
}
