package com.io.linkapp.user.service;

import com.io.linkapp.exception.UserNotFoundException;
import com.io.linkapp.user.domain.User;
import com.io.linkapp.user.mapper.UserMapper;
import com.io.linkapp.user.repository.UserRepository;
import com.io.linkapp.user.request.Oauth2UserRequest;
import com.io.linkapp.user.request.UserRequest;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
                .orElseThrow(UserNotFoundException::new);
    }

    public Optional<User> findOauthUser(String username) {
        return userRepository.findByUsername(username);
    }

    public void signup(UserRequest userRequest) {
        userRequest.encodePassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
        User user = UserMapper.INSTANCE.toEntity(userRequest);
        userRepository.save(user);
    }

    public void oauth2Signup(Oauth2UserRequest userRequest) {
        User user = UserMapper.INSTANCE.toEntity(userRequest);
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
