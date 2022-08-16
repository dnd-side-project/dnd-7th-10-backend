package com.io.linkapp.link.repository;

import com.io.linkapp.link.domain.Memo;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.io.linkapp.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo, UUID> {

    List<Memo> findByUser(User user);
}
