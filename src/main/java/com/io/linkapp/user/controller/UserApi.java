package com.io.linkapp.user.controller;

import com.io.linkapp.user.domain.User;
import com.io.linkapp.user.request.UserRequest;
import com.io.linkapp.user.service.UserService;
import io.swagger.annotations.Api;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/user/{username}")
    public User getUser(@PathVariable("username") String username){
        return userService.findByUsername(username);
    }

    @PostMapping("/user")
    public String join(@RequestBody @Valid UserRequest userRequest) {
        userService.signup(userRequest);
        return "회원가입 완료";
    }

    @GetMapping("/api/member")
    public ResponseEntity<User> findMember() {
        return ResponseEntity.ok().body(userService.findById(1L));
    }

    @GetMapping("/api/members")
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok().body(userService.findAll());
    }
}
