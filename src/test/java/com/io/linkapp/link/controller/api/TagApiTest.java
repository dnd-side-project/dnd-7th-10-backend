package com.io.linkapp.link.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.io.linkapp.link.request.TagRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
public class TagApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private TagRequest.Add addRequest;

    @Test
    @DisplayName("POST: /folder/add")
    public void addTest() throws Exception {


    }

}
