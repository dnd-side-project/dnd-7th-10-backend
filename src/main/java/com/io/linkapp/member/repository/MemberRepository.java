package com.io.linkapp.member.repository;

import com.io.linkapp.member.domain.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {
}
