package com.io.linkapp.member.controller;

import com.io.linkapp.member.domain.Member;
import com.io.linkapp.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/api/register")
    public ResponseEntity save() {
        memberService.save();
        return ResponseEntity.ok().body("save complete");
    }

    @GetMapping("/api/member")
    public ResponseEntity<Member> findMember() {
        return ResponseEntity.ok().body(memberService.findById(1L));
    }

    @GetMapping("/api/members")
    public ResponseEntity<List<Member>> findAll() {
        return ResponseEntity.ok().body(memberService.findAll());
    }
}
