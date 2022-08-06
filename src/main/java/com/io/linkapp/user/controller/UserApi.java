package com.io.linkapp.user.controller;

import com.io.linkapp.user.domain.User;
import com.io.linkapp.user.request.UserRequest;
import com.io.linkapp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserApi {

    private final UserService userService;

    @PostMapping("/user")
    public String join(@RequestBody UserRequest userRequest) {
        userService.save(userRequest);
        return "회원가입 완료";
    }

    @GetMapping("/user")
    public String user(){
        return "hi~";
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
