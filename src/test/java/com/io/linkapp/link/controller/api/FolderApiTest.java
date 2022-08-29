package com.io.linkapp.link.controller.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.io.linkapp.config.security.jwt.JwtProperty;
import com.io.linkapp.link.domain.Folder;
import com.io.linkapp.link.repository.FolderRepository;
import com.io.linkapp.user.domain.User;
import com.io.linkapp.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class FolderApiTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    FolderRepository folderRepository;

    @Autowired
    UserRepository userRepository;

    private String jwtToken;
    private User user;

    @BeforeEach
    void folderSetup(){
        User build = User.builder()
                .username("testUser")
                .password("test")
                .build();

        user = userRepository.save(build);

        jwtToken = JWT.create()
                .withSubject(JwtProperty.SUBJECT)
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperty.EXPIRATION_TIME))
                .withClaim("username", "testUser")
                .sign(Algorithm.HMAC512(JwtProperty.SECRET));
    }

    @DisplayName("GET /folder/{folderId} 요청 시 일치하는 폴더를 반환한다")
    @Test
    void findFolderTest() throws Exception {
        Folder folder = Folder.builder()
                .folderTitle("testFolder")
                .user(user)
                .build();

        folder = folderRepository.save(folder);


        mockMvc.perform(get("/folder/{folderId}", folder.getFolderId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.folderTitle").value("testFolder"))
                .andDo(print());
    }

    @DisplayName("폴더 상세 조회 시 일치하는 폴더가 없으면 404를 반환한다")
    void folderExceptionTest() throws Exception {

    }

    @DisplayName("POST /folder 요청 시 폴더를 저장한다")
    @Test
    void addFolderTest() throws Exception {
    }

    @DisplayName("Delete /folder/{folderId} 요청 시 폴더를 삭제한다")
    @Test
    void removeFolderTest() throws Exception {

    }

    @DisplayName("폴더 삭제 시 폴더가 없으면 404를 반환한다")
    @Test
    void removeFolderExceptionTest(){

    }

}