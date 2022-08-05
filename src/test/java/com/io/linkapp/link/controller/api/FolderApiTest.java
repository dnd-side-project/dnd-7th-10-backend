package com.io.linkapp.link.controller.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.io.linkapp.link.controller.mapper.FolderFormMapper;
import com.io.linkapp.link.request.FolderRequest;
import com.io.linkapp.link.response.FolderResponse;
import com.io.linkapp.link.service.FolderService;
import java.util.UUID;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * - Given
 *
 * 테스트를 위해 데이터들을 준비하는 과정이다.
 *
 * - When
 *
 * 실제로 요청을 하거나 실행을 해보는 과정이다.
 *
 * - Then
 *
 * 실행한 결과를 검증하는 과정이다.
 */


//MockMvc는 가상의 클라이언트로 어플리케이션에 요청을 날리는 역할
//Mock 테스트시 필요한 의존성을 제공,컨트롤러처럼 엔드포인트가 있는 테스트를 진행할 때는 MockMvc를 활용
//요청을 테스트할때 사용되는 mockMVC를 자동으로 설정해주는 어노테이션
@AutoConfigureMockMvc
//모든 Bean들을 로드해서 실제 구동되는 서버와 같게 콘텍스트를 로드하여 테스트하기 때문에 실제로 서버가 응답하는 결과를 확인해 볼 수 있다
//단위 테스트와같이 기능검증을 위한 것이 아니라 spring framework에 전체적으로 플로우가 제대로 동작하는지 검증하기 위해 사용
@SpringBootTest
public class FolderApiTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    ObjectMapper objectMapper;
    
    @Autowired
    private WebApplicationContext ctx;
    
    @Autowired
    private FolderService folderService;
    
    @Autowired
    private FolderFormMapper formMapper;
    
    private FolderRequest.GetAll getAllRequest;
    
    private FolderRequest.Add addRequest;
    
    private FolderResponse.GetAll addResponse;
    
    private FolderRequest.Modify modifyRequest;
    
    //private FolderRequest.Remove removeReqeust;
    
 //애노테이션을 이용한 자동 주입 이외에도, 스프링 프레임워크에서 사용하는 것 처럼 MockMvc를 직접 생성하는 방법
//    @Before
//    public void before() {
//        mockMvc = MockMvcBuilders.standaloneSetup(MyController.class)
//            .alwaysExpect(MockMvcResultMatchers.status().isOk())
//            .build();
//    }
    
//    @BeforeEach
//    public void setup() {
//        mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
//            .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 한글 깨짐 처리
//            .build();
//    }
    
    
    @Test
    @DisplayName("POST: /folder/add")
    public void addTest() throws Exception {
    
        //given
        addRequest = FolderRequest.Add.builder()
            .folderTitle("test folder").build();
        
        //when
        mockMvc.perform(post("/folder/add")
            .content(objectMapper.writeValueAsString(addRequest))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            //.andExpect(content().string(String.valueOf(addRequest)))
            .andDo(print());
    }
    
    
    
    
    
}
