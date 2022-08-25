//package com.io.linkapp.link.controller.api;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.io.linkapp.link.request.TagRequest;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.web.servlet.MockMvc;
//
////MockMvc는 가상의 클라이언트로 어플리케이션에 요청을 날리는 역할
////Mock 테스트시 필요한 의존성을 제공,컨트롤러처럼 엔드포인트가 있는 테스트를 진행할 때는 MockMvc를 활용
////요청을 테스트할때 사용되는 mockMVC를 자동으로 설정해주는 어노테이션
//@AutoConfigureMockMvc
////모든 Bean들을 로드해서 실제 구동되는 서버와 같게 콘텍스트를 로드하여 테스트하기 때문에 실제로 서버가 응답하는 결과를 확인해 볼 수 있다
////단위 테스트와같이 기능검증을 위한 것이 아니라 spring framework에 전체적으로 플로우가 제대로 동작하는지 검증하기 위해 사용
//@SpringBootTest
//public class TagApiTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    private TagRequest.Add addRequest;
//
//    @Test
//    @DisplayName("POST: /folder/add")
//    public void addTest() throws Exception {
//
//
//    }
//
//}
