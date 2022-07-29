package com.io.linkapp.link.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.io.linkapp.link.domain.Memo;
import com.io.linkapp.link.repository.MemoRepository;
import com.io.linkapp.link.service.MemoService;
import com.io.linkapp.request.MemoRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MemoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MemoService memoService;

    @Autowired
    MemoRepository memoRepository;

    @Test
    @DisplayName("POST: api/memo 요청 시 메모가 저장된다.")
    void saveMemoTest() throws Exception {
        //given
        UUID uuid = UUID.randomUUID();
        MemoRequest request = MemoRequest.builder()
            .articleId(uuid)
            .content("새로운 메모")
            .build();

        //expected
        mockMvc.perform(post("/api/memo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("POST: 단건 메모 저장 시 NULL, 공백이 들어가면 400에러를 반환한다")
    void saveNullMemo() throws Exception {
        //given
        UUID uuid = UUID.randomUUID();
        MemoRequest request = MemoRequest.builder()
                .articleId(uuid)
                .content("")
                .build();

        //expected
        mockMvc.perform(post("/api/memo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("내용을 입력해주세요."))
                .andExpect(jsonPath("$.code").value(400))
                .andDo(print());
    }

    @Test
    @DisplayName("POST: 단건 메모 저장 시 공백만 아주 많이 들어가도 400 에러를 반환한다")
    void saveNullRecursiveMemo() throws Exception {
        //given
        UUID uuid = UUID.randomUUID();
        MemoRequest request = MemoRequest.builder()
                .articleId(uuid)
                .content("                  ")
                .build();

        //expected
        mockMvc.perform(post("/api/memo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("내용을 입력해주세요."))
                .andExpect(jsonPath("$.code").value(400))
                .andDo(print());
    }

    @Test
    @DisplayName("GET: api/memo/{id} 요청 시 해당 메모를 불러온다")
    void findMemoById() throws Exception {
        //given
        UUID uuid = UUID.randomUUID();
        Memo request = Memo.builder()
            .articleId(uuid)
            .content("새로운 메모")
            .build();

        memoRepository.save(request);

        //expected
        mockMvc.perform(get("/api/memo/{id}", request.getId())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(request.getId().toString()))
            .andExpect(jsonPath("$.articleId").value(uuid.toString()))
            .andExpect(jsonPath("$.content").value("새로운 메모"))
            .andDo(print());
    }

    @Test
    @DisplayName("GET: 없는 메모를 조회 시 404 에러를 반환한다")
    void findNotExistedMemo() throws Exception {
        //expected
        mockMvc.perform(get("/api/memo/{id}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("존재하지 않는 메모입니다."))
                .andDo(print());
    }
}