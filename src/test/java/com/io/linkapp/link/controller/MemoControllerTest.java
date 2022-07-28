package com.io.linkapp.link.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.io.linkapp.link.domain.Memo;
import com.io.linkapp.link.repository.MemoRepository;
import com.io.linkapp.link.service.MemoService;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
        Memo request = Memo.builder()
            .articleId(uuid)
            .content("새로운 메모")
            .build();

        //when
        mockMvc.perform(post("/api/memo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
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
    @DisplayName("GET: api/memos 요청 시 모든 메모를 불러온다")
    void findAllMemoTest() throws Exception {
        mockMvc.perform(get("/api/memo")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }
}