package com.io.linkapp.link.controller.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.io.linkapp.config.security.jwt.JwtProperty;
import com.io.linkapp.exception.CustomGlobalException;
import com.io.linkapp.exception.ErrorCode;
import com.io.linkapp.link.domain.Article;
import com.io.linkapp.link.domain.Folder;
import com.io.linkapp.link.mapper.ArticleMapper;
import com.io.linkapp.link.repository.ArticleRepository;
import com.io.linkapp.link.repository.FolderRepository;
import com.io.linkapp.link.repository.MemoRepository;
import com.io.linkapp.link.request.ArticleRequest;
import com.io.linkapp.user.domain.User;
import com.io.linkapp.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class ArticleApiTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    FolderRepository folderRepository;

    @Autowired
    MemoRepository memoRepository;

    @Autowired
    UserRepository userRepository;


    private ArticleRequest request;
    private String jwtToken;

    @BeforeEach
    void setUp() {
        User user = User.builder()
            .username("testUser")
            .password("test")
            .build();

        user = userRepository.save(user);

        Folder folder = Folder.builder()
            .user(user)
            .folderTitle("testFolder")
            .build();

        folder = folderRepository.save(folder);

        request = ArticleRequest.builder()
            .folderId(folder.getFolderId())
            .linkUrl("www.naver.com")
            .build();

        jwtToken = JWT.create()
            .withSubject(JwtProperty.SUBJECT)
            .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperty.EXPIRATION_TIME))
            .withClaim("username", "testUser")
            .sign(Algorithm.HMAC512(JwtProperty.SECRET));
    }

    @Test
    @DisplayName("POST: /article 요청 시 링크가 저장된다")
    void addTest() throws Exception {
        //expected
        mockMvc.perform(post("/article")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header(JwtProperty.HEADER, JwtProperty.TOKEN_PREFIX + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.linkUrl").value("www.naver.com"))
                .andDo(print());
    }

    @Test
    @DisplayName("DELETE: /article/{id} 요청 시 링크를 삭제한다")
    void removeArticleTest() throws Exception {
        //given
        Article article = articleRepository.save(ArticleMapper.INSTANCE.toEntity(request));

        //when
        mockMvc.perform(delete("/article/{id}", article.getId()))
            .andExpect(status().isOk())
            .andDo(print());

        //then
        assertThrows(CustomGlobalException.class,
            () -> articleRepository.findById(article.getId())
                .orElseThrow(() -> new CustomGlobalException(ErrorCode.ARTICLE_NOT_FOUND)));
    }
}