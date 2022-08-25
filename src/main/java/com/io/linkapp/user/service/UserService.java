package com.io.linkapp.user.service;

import com.io.linkapp.config.security.jwt.JwtResponse;
import com.io.linkapp.config.security.jwt.JwtTokenProvider;
import com.io.linkapp.exception.CustomGlobalException;
import com.io.linkapp.exception.ErrorCode;
import com.io.linkapp.link.domain.Folder;
import com.io.linkapp.link.domain.Remind;
import com.io.linkapp.link.repository.FolderRepository;
import com.io.linkapp.link.repository.RemindRepository;
import com.io.linkapp.link.request.KakaoRequest;
import com.io.linkapp.user.domain.User;
import com.io.linkapp.user.mapper.UserMapper;
import com.io.linkapp.user.repository.UserRepository;
import com.io.linkapp.user.request.Oauth2UserRequest;
import com.io.linkapp.user.request.UserRequest;
import com.io.linkapp.user.response.UserResponse;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final FolderRepository folderRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final RedisService redisService;
    private final RemindRepository remindRepository;

    public User findUserById(UUID id){
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomGlobalException(ErrorCode.USER_NOT_FOUND));
    }

    public UserResponse findUser(String username) {
        return UserMapper.INSTANCE.toResponseDto(userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomGlobalException(ErrorCode.USER_NOT_FOUND)));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).
                orElseThrow(() -> new CustomGlobalException(ErrorCode.USER_NOT_FOUND));
    }

    public Optional<User> findOauthUser(String username) {
        return userRepository.findByUsername(username);
    }

    public User join(UserRequest userRequest) {
        if(userRepository.findByUsername(userRequest.getUsername()).isPresent()){
            throw new CustomGlobalException(ErrorCode.EXIST_USER);
        }


        userRequest.encodePassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
        User user = UserMapper.INSTANCE.toEntity(userRequest);
        
        
        Folder defaultFolder = Folder.builder()
                .folderTitle("기본 폴더")
                .user(user)
                .build();

        User savedUser = userRepository.save(user);
    
        Remind remind = Remind.builder()
            .userId(savedUser.getId())
            //.remindTitle(user.getUsername() + "님의 default 리마인드")
            .remindTitle("default")
            .build();
    
        remind = remindRepository.save(remind);
        folderRepository.save(defaultFolder);
        return savedUser;
    }

    public JwtResponse kakaoLogin(KakaoRequest kakaoRequest) {
        Optional<User> user = userRepository.findByUsername(kakaoRequest.getUserEmail());
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(redisService);

        if(user.isPresent()){
            String username = user.get().getUsername();
            return jwtTokenProvider.provideToken(username);
        } else {
            User newUser = User.builder()
                .username(kakaoRequest.getUserEmail())
                .build();

            Folder defaultFolder = Folder.builder()
                .folderTitle("기본 폴더")
                .user(newUser)
                .build();

            newUser = userRepository.save(userRepository.save(newUser));
            
            Remind remind = Remind.builder()
                .userId(newUser.getId())
                .remindTitle("default")
                .build();
    
            remind = remindRepository.save(remind);
            folderRepository.save(defaultFolder);
            return jwtTokenProvider.provideToken(newUser.getUsername());
        }
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
