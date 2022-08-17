package com.io.linkapp.user.repository;

import com.io.linkapp.user.domain.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    
    User findUserById(UUID id);
}
