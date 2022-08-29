package com.io.linkapp.link.controller.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.io.linkapp.config.security.jwt.JwtProperty;
import com.io.linkapp.exception.CustomGlobalException;
import com.io.linkapp.link.domain.Article;
import com.io.linkapp.link.domain.Folder;
import com.io.linkapp.link.domain.Memo;
import com.io.linkapp.link.repository.ArticleRepository;
import com.io.linkapp.link.repository.FolderRepository;
import com.io.linkapp.link.repository.MemoRepository;
import com.io.linkapp.link.request.MemoRequest;
import com.io.linkapp.link.service.MemoService;
import com.io.linkapp.user.domain.User;
import com.io.linkapp.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemoApiTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MemoService memoService;

    @Autowired
    MemoRepository memoRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FolderRepository folderRepository;

    private Article tempArticle;
    private String jwtToken;

    @BeforeEach
    public void createArticle(){
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

        Article article = Article.builder()
                .user(user)
                .folder(folder)
                .linkUrl("www.daum.net")
                .build();

        tempArticle = articleRepository.save(article);

        jwtToken = JWT.create()
                .withSubject(JwtProperty.SUBJECT)
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperty.EXPIRATION_TIME))
                .withClaim("username", "testUser")
                .sign(Algorithm.HMAC512(JwtProperty.SECRET));
    }

//    @Test
//    @DisplayName("POST: /memo 요청 시 메모가 저장된다.")
//    void saveMemoTest() throws Exception {
//        //given
//        MemoRequest request = MemoRequest.builder()
//            .articleId(tempArticle.getId())
//            .content("testMemo")
//            .build();
//
//        //expected
//        mockMvc.perform(post("/memo")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request))
//                        .header(JwtProperty.HEADER, JwtProperty.TOKEN_PREFIX + jwtToken))
//                .andExpect(status().isOk())
//                .andDo(print());
//    }

    @Test
    @DisplayName("POST: 단건 메모 저장 시 NULL, 공백이 들어가면 400에러를 반환한다")
    void saveNullMemo() throws Exception {
        //given
        MemoRequest request = MemoRequest.builder()
            .articleId(tempArticle.getId())
            .content("")
            .build();

        //expected
        mockMvc.perform(post("/memo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    @Test
    @DisplayName("POST: 단건 메모 저장 시 공백만 아주 많이 들어가도 400 에러를 반환한다")
    void saveNullRecursiveMemo() throws Exception {
        //given
        MemoRequest request = MemoRequest.builder()
            .articleId(tempArticle.getId())
            .content("               ")
            .build();

        //expected
        mockMvc.perform(post("/memo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    @Test
    @DisplayName("GET: /memo/{id} 요청 시 해당 메모를 불러온다")
    void findMemoById() throws Exception {
        //given
        Memo memo = Memo.builder()
                .user(tempArticle.getUser())
                .article(tempArticle)
                .content("testMemo")
                .build();

        memo = memoRepository.save(memo);

        //expected
        mockMvc.perform(get("/memo/{id}", memo.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.folderTitle").value("testFolder"))
                .andExpect(jsonPath("$.id").value(memo.getId().toString()))
                .andExpect(jsonPath("$.content").value("testMemo"))
                .andDo(print());
    }

    @Test
    @DisplayName("GET: 없는 메모를 조회 시 404 에러를 반환한다")
    void findNotExistedMemo() throws Exception {
        //expected
        mockMvc.perform(get("/memo/{id}", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andDo(print());
    }

    @Test
    @DisplayName("PATCH: /memo/{id} 로 메모 내용을 변경한다.")
    void editMemo() throws Exception {
        //given
        Memo memo = Memo.builder()
            .article(tempArticle)
            .content("메모")
            .build();

        memoRepository.save(memo);

        String patchMemo = "메모수정";

        //when
        mockMvc.perform(patch("/memo/{id}", memo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(patchMemo))
            .andExpect(status().isOk())
            .andDo(print());

        //then
        String content = memoRepository.findById(memo.getId()).get().getContent();
        Assertions.assertEquals("메모수정", content);
    }

    @Test
    @DisplayName("DELETE: /memo/{id} 메모 삭제 시 정상적으로 삭제 된다")
    void deleteMemo() throws Exception {
        //given
        Memo temporaryMemo = Memo.builder()
                .article(tempArticle)
            .content("임시메모")
            .build();

        memoRepository.save(temporaryMemo);

        //when
        UUID memoId = temporaryMemo.getId();
        mockMvc.perform(delete("/memo/{id}", memoId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());

        //then
        Assertions.assertThrows(CustomGlobalException.class,
            () -> memoService.findById(memoId));
    }

    @Test
    @DisplayName("DELETE: /memo/{id} 없는 메모를 삭제 시 404를 반환한다")
    void deleteNotExistMemo() throws Exception {
        mockMvc.perform(delete("/memo/{id}", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andDo(print());
    }
}