//package com.io.linkapp.link.controller.api;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.io.linkapp.exception.MemoNotFoundException;
//import com.io.linkapp.link.domain.Article;
//import com.io.linkapp.link.domain.Memo;
//import com.io.linkapp.link.repository.ArticleRepository;
//import com.io.linkapp.link.repository.MemoRepository;
//import com.io.linkapp.link.request.MemoRequest;
//import com.io.linkapp.link.service.MemoService;
//import java.util.UUID;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@Transactional
//class MemoApiTest {
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @Autowired
//    MemoService memoService;
//
//    @Autowired
//    MemoRepository memoRepository;
//
//    @Autowired
//    ArticleRepository articleRepository;
//
//    private static Article article;
//
//    @BeforeEach
//    public void createArticle(){
//       article = Article.builder()
//            .folderId(UUID.randomUUID())
//            .remindId(UUID.randomUUID())
//            .linkTitle("링크제목")
//            .linkContent("링크내용")
//            .build();
//
//        articleRepository.save(article);
//    }
//
//    @Test
//    @DisplayName("POST: /memo 요청 시 메모가 저장된다.")
//    void saveMemoTest() throws Exception {
//        //given
//        MemoRequest request = MemoRequest.builder()
//            .articleId(article.getId())
//            .content("새로운 메모")
//            .build();
//
//        //expected
//        mockMvc.perform(post("/memo")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(request)))
//            .andExpect(status().isOk())
//            .andDo(print());
//    }
//
//    @Test
//    @DisplayName("POST: 단건 메모 저장 시 NULL, 공백이 들어가면 400에러를 반환한다")
//    void saveNullMemo() throws Exception {
//        //given
//        MemoRequest request = MemoRequest.builder()
//            .articleId(article.getId())
//            .content("")
//            .build();
//
//        //expected
//        mockMvc.perform(post("/memo")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(request)))
//            .andExpect(status().isBadRequest())
//            .andExpect(jsonPath("$.message").value("내용을 입력해주세요."))
//            .andExpect(jsonPath("$.code").value(400))
//            .andDo(print());
//    }
//
//    @Test
//    @DisplayName("POST: 단건 메모 저장 시 공백만 아주 많이 들어가도 400 에러를 반환한다")
//    void saveNullRecursiveMemo() throws Exception {
//        //given
//        MemoRequest request = MemoRequest.builder()
//            .articleId(article.getId())
//            .content("               ")
//            .build();
//
//        //expected
//        mockMvc.perform(post("/memo")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(request)))
//            .andExpect(status().isBadRequest())
//            .andExpect(jsonPath("$.message").value("내용을 입력해주세요."))
//            .andExpect(jsonPath("$.code").value(400))
//            .andDo(print());
//    }
//
//    @Test
//    @DisplayName("GET: /memo/{id} 요청 시 해당 메모를 불러온다")
//    void findMemoById() throws Exception {
//        //given
//        Memo request = Memo.builder()
//            .article(article)
//            .content("새로운 메모")
//            .build();
//
//        memoRepository.save(request);
//
//        //expected
//        mockMvc.perform(get("/memo/{id}", request.getId())
//                .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(jsonPath("$.id").value(request.getId().toString()))
//            .andExpect(jsonPath("$.article.id").value(article.getId().toString()))
//            .andExpect(jsonPath("$.content").value("새로운 메모"))
//            .andDo(print());
//    }
//
//    @Test
//    @DisplayName("GET: 없는 메모를 조회 시 404 에러를 반환한다")
//    void findNotExistedMemo() throws Exception {
//        //expected
//        mockMvc.perform(get("/memo/{id}", UUID.randomUUID())
//                .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNotFound())
//            .andExpect(jsonPath("$.code").value(404))
//            .andExpect(jsonPath("$.message").value("존재하지 않는 메모입니다."))
//            .andDo(print());
//    }
//
//    @Test
//    @DisplayName("PATCH: /memo/{id} 로 메모 내용을 변경한다.")
//    void editMemo() throws Exception {
//        //given
//        Memo memo = Memo.builder()
//            .article(article)
//            .content("메모")
//            .build();
//
//        memoRepository.save(memo);
//
//        MemoRequest memoRequest = MemoRequest.builder()
//            .content("메모수정")
//            .build();
//
//        //when
//        mockMvc.perform(patch("/memo/{id}", memo.getId())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(memoRequest)))
//            .andExpect(status().isOk())
//            .andDo(print());
//
//        //then
//        String content = memoRepository.findById(memo.getId()).get().getContent();
//        Assertions.assertEquals("메모수정", content);
//    }
//
//    @Test
//    @DisplayName("DELETE: /memo/{id} 메모 삭제 시 정상적으로 삭제 된다")
//    void deleteMemo() throws Exception {
//        //given
//        Memo temporaryMemo = Memo.builder()
//                .article(article)
//            .content("임시메모")
//            .build();
//
//        memoRepository.save(temporaryMemo);
//
//        //when
//        UUID memoId = temporaryMemo.getId();
//        mockMvc.perform(delete("/memo/{id}", memoId)
//                .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isOk())
//            .andDo(print());
//
//        //then
//        Assertions.assertThrows(MemoNotFoundException.class,
//            () -> memoService.findById(memoId));
//    }
//
//    @Test
//    @DisplayName("DELETE: /memo/{id} 없는 메모를 삭제 시 404를 반환한다")
//    void deleteNotExistMemo() throws Exception {
//        mockMvc.perform(delete("/memo/{id}", UUID.randomUUID())
//                .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNotFound())
//            .andExpect(jsonPath("$.code").value(404))
//            .andExpect(jsonPath("$.message").value("존재하지 않는 메모입니다."))
//            .andDo(print());
//    }
//}