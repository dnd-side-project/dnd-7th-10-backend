package com.io.linkapp.link.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.io.linkapp.exception.ArticleNotFoundException;
import com.io.linkapp.link.domain.Article;
import com.io.linkapp.link.domain.Memo;
import com.io.linkapp.link.mapper.ArticleMapper;
import com.io.linkapp.link.repository.ArticleRepository;
import com.io.linkapp.link.repository.MemoRepository;
import com.io.linkapp.link.request.ArticleRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class ArticleApiTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    MemoRepository memoRepository;

    private static ArticleRequest request;

    @BeforeEach
    void articleSetup(){
        UUID uuid = UUID.randomUUID();

       request = ArticleRequest.builder()
                .folderId(uuid)
                .remindId(uuid)
                .userId(uuid)
                .linkContent("getTest")
                .linkTitle("getTest")
                .build();
    }

    @Test
    @DisplayName("POST: /article 요청 시 링크가 저장된다")
    void addTest() throws Exception {
        //expected
        mockMvc.perform(post("/article")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("GET: /article/{id}로 링크를 조회한다")
    void getLink() throws Exception{
        Article article = ArticleMapper.INSTANCE.toEntity(request);

        articleRepository.save(article);

        //expected
        mockMvc.perform(get("/article/{id}", article.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(article.getId().toString()))
                .andExpect(jsonPath("$.linkContent").value("getTest"))
                .andDo(print());
    }

    @Test
    @DisplayName("아티클 조회 시 아티클에 등록된 메모 모두 불러온다")
    void getArticleMemoTest() throws Exception {
        //given
        Article article = ArticleMapper.INSTANCE.toEntity(request);

        articleRepository.save(article);

        Memo memo = Memo.builder()
                .article(article)
                .content("아티클에 등록된 메모 첫 번째")
                .build();

        Memo memo2 = Memo.builder()
                .article(article)
                .content("아티클에 등록된 메모 두 번째")
                .build();

        memoRepository.save(memo);
        memoRepository.save(memo2);

        //when
        mockMvc.perform(get("/article/{id}", article.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
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
        Assertions.assertThrows(ArticleNotFoundException.class,
                () -> articleRepository.findById(article.getId())
                        .orElseThrow(ArticleNotFoundException::new));
    }
}