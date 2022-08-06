package com.io.linkapp.link.controller.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.io.linkapp.link.controller.mapper.FolderFormMapper;
import com.io.linkapp.link.controller.predicate.FolderFormPredicate;
import com.io.linkapp.link.domain.Folder;
import com.io.linkapp.link.repository.FolderRepository;
import com.io.linkapp.link.request.FolderRequest;
import com.io.linkapp.link.request.FolderRequest.Add;
import com.io.linkapp.link.request.FolderRequest.GetAll;
import com.io.linkapp.link.response.FolderResponse;
import com.io.linkapp.link.service.FolderService;
import com.querydsl.core.BooleanBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
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

//reference: https://theheydaze.tistory.com/218

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
    
    //As you mock the PersonService you have to provide its behaviour otherwise it always returns null.
    // You can use when().thenReturn() from Mockito for this:
    //대신 응답 코드는 따로 명시해주지 않아도 잘 나오는 듯
    //얘를 autowired로 해줘서 에러가 났던 것 ㅠ//해당 빈이 현재 컨테이너 안에 존재해야 하므로 @MockBean을 써준다
    //... 근데 이러면 결국 swagger가 더 편하지 않나여,,?
    @MockBean
    private FolderService service;
    
    @Autowired
    private FolderFormMapper formMapper;
   
    
    private FolderRequest.GetAll getAllRequest;
    private FolderRequest.Add addRequest;
    
    private FolderResponse.GetAll addResponse;
    
    private FolderRequest.Modify modifyRequest;
    
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
        addRequest = new FolderRequest.Add();
        addRequest.setFolderTitle("new new new folder");
    
        //service 의 save 함수의 테스트 결과 명시해 놓은 것
        // 왜냐면  @MockBean 들은 모두 "가짜" 객체이므로 어떤 동작을 하는지- 즉 테스트가 어떤 결과가 나와야 하는지 명시해줘야 함
        //As you mock the PersonService you have to provide its behaviour otherwise it always returns null.
        // You can use when().thenReturn() from Mockito for this:
        Folder folder = formMapper.toFolder(addRequest);
        UUID uuid = UUID.randomUUID();
        folder.setFolderId(uuid);
        given(service.add(any())).willReturn(folder);
      
        //when
        MvcResult mvcResult = mockMvc.perform(post("/folder/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(addRequest)) //Request Body에 문자열을 설정
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.folderTitle").value("new new new folder"))
            .andDo(print())
            .andReturn();
        
        /*
        MockHttpServletResponse:
           Status = 200
    Error message = null
          Headers = [Content-Type:"application/json"]
     Content type = application/json
             Body = {"folderId":"4411bcbc-c962-43cb-988e-3b55bd98fa1f","folderTitle":"new new new folder"}
    Forwarded URL = null
   Redirected URL = null
          Cookies = []
         */
    }
    
    /**
     * 페이징쪽은 아직 테스트 코드 관련해서 더 찾아봐야 할 것 같습니다,,,
     * 혹시 의견 있으시면 바로 주세용
     */
//    @Test
//    @DisplayName("get: 페이징 조회")
//    public void pagingTest() throws Exception {
//        //reference: https://gocheat.github.io/spring/spring_test-1/
//
//        //given - 페이징 검색 조건
//        getAllRequest = new FolderRequest.GetAll();
//        getAllRequest.setFolderTitle("");
//
//        //service의 getPage 동작 정의, 즉 getPage시의 기대 결과 - mockBean이므로
//        BooleanBuilder builder = new BooleanBuilder();
//        PageRequest pageable = PageRequest.of(0, 10);
//
//        List<Folder> folders = new ArrayList<>();
//        Folder folder1 =new Folder();
//        UUID uuid = UUID.randomUUID();
//        folder1.setFolderId(uuid);
//        folder1.setFolderTitle("first");
//        folders.add(folder1);
//
//        Folder folder2 =new Folder();
//        UUID uuid2 = UUID.randomUUID();
//        folder2.setFolderId(uuid2);
//        folder2.setFolderTitle("second");
//        folders.add(folder2);
//
//        Page<Folder> pagedResponse = new PageImpl(folders);
//        //Mockito.when(service.getPage(builder, Mockito.any())).thenReturn(pagedResponse);
//
//        doReturn(Optional.of(pagedResponse))
//            .when(service.getPage(builder, Mockito.any()));
//
//        //then
//        MvcResult mvcResult = mockMvc.perform(get("/folder")
//                .accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isOk())
//            //.andExpect(jsonPath("$.folderTitle").value("new new new folder"))
//            .andDo(print())
//            .andReturn();
//
//    }


    // 삭제와 modify는 서비스 단에 구현하였음

//    @Test
//    @DisplayName("POST: /folder/{id}")
//    public void modifyTest() throws Exception {
//
//        //given
//        modifyRequest = new FolderRequest.Modify();
//        modifyRequest.setFolderTitle("modified folder");
//
//        Folder folder = formMapper.toFolder(modifyRequest);
//        UUID uuid = UUID.randomUUID();
//        folder.setFolderId(uuid);
//        given(service.modify(uuid,any())).willReturn(folder);
//
//        //when
//        mockMvc.perform(post("/folder/{id}", folder.getFolderId())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(modifyRequest)))
//            .andExpect(status().isOk())
//            .andDo(print());
//
//
//    }
//
    
    
    
}
