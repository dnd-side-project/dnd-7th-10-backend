package com.io.linkapp.user.service;

import com.io.linkapp.exception.CustomGlobalException;
import com.io.linkapp.exception.ErrorCode;
import com.io.linkapp.link.domain.Folder;
import com.io.linkapp.link.repository.FolderRepository;
import com.io.linkapp.user.domain.User;
import com.io.linkapp.user.mapper.UserMapper;
import com.io.linkapp.user.repository.UserRepository;
import com.io.linkapp.user.request.Oauth2UserRequest;
import com.io.linkapp.user.request.UserRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.io.linkapp.user.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final FolderRepository folderRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    public UserResponse findUser(String username) {
        return UserMapper.INSTANCE.toResponseDto(userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomGlobalException(ErrorCode.USER_NOT_FOUND)));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomGlobalException(ErrorCode.USER_NOT_FOUND));
    }

    public Optional<User> findOauthUser(String username) {
        return userRepository.findByUsername(username);
    }

    public User join(UserRequest userRequest) {
        userRequest.encodePassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
        User user = UserMapper.INSTANCE.toEntity(userRequest);

        Folder defaultFolder = Folder.builder()
                .folderTitle("기본 폴더")
                .user(user)
                .build();

        User savedUser = userRepository.save(user);
        folderRepository.save(defaultFolder);

        return savedUser;
    }

    public void oauth2Signup(Oauth2UserRequest userRequest) {
        User user = UserMapper.INSTANCE.toEntity(userRequest);
        userRepository.save(user);
    }

    public List<UserResponse> findAll() {
        return userRepository.findAll().stream()
                .map(user -> UserMapper.INSTANCE.toResponseDto(user))
                .collect(Collectors.toList());
    }
}
