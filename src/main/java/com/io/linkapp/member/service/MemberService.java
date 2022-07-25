package com.io.linkapp.member.service;

import com.io.linkapp.member.domain.Member;
import com.io.linkapp.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Member findById(Long id) {
        return memberRepository.findById(id).get();
    }

    public void save() {
        Member member = new Member();
        memberRepository.save(member);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }
}
