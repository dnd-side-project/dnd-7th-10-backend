package com.io.linkapp.user.service;

import com.io.linkapp.user.domain.User;
import com.io.linkapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(RuntimeException::new);
    }

    public void save(User user) {
        userRepository.save(user.passwordEncode(bCryptPasswordEncoder));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
