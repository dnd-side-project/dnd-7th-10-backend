package com.io.linkapp.link.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.io.linkapp.link.controller.mapper.FolderFormMapperImpl;
import com.io.linkapp.link.domain.Folder;
import com.io.linkapp.link.mapper.FolderMapperImpl;
import com.io.linkapp.link.repository.FolderRepository;
import com.querydsl.core.BooleanBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

//reference : https://galid1.tistory.com/772   https://jiminidaddy.github.io/dev/2021/05/20/dev-spring-%EB%8B%A8%EC%9C%84%ED%85%8C%EC%8A%A4%ED%8A%B8-Repository/

@ExtendWith(MockitoExtension.class)
public class FolderServiceTest {
  
    @InjectMocks //@Mock를 통해 만들어진 (가짜) 객체들로 자신을 생성
    private FolderService service;
    
    @Mock
    private FolderRepository folderRepository;
    
//    @Mock
//    private FolderMapperImpl folderMapper;
    
    
    @Test
    @DisplayName("add test")
    public void add_test(){
        //given
        UUID uuid = UUID.randomUUID();
        Folder folder = new Folder(uuid,"test add folder");
        
        //mocking
        // 프로그램에서 작성한 실제 ArticleRepository와 달리 가짜로 만들어낸 Mock ArticleRepository 객체는 별다른 동작 기술 없이는 동작할 수 없다.
        // 따라서 테스트 케이스마다 해당 함수 내부에서 이 객체가 어떻게 동작돼야 하는지 명시적으로 작성해줘야한다.
        given(folderRepository.save(any())).willReturn(folder);
        given(folderRepository.findById(uuid)).willReturn(Optional.ofNullable(folder));
        
        //when
        UUID folderId = service.add(folder).getFolderId();
        
        //then
        Folder findFolder = folderRepository.findById(folderId).get();
        
        assertEquals(folder.getFolderId(),findFolder.getFolderId());
        assertEquals(folder.getFolderTitle(),findFolder.getFolderTitle());
      
    }
    
    @Test
    @DisplayName("read test")
    public void read_test(){
        //given
        UUID uuid = UUID.randomUUID();
        Folder folder1 = new Folder(uuid,"folder1");
        Folder folder2 = new Folder(uuid,"folder2");
    
        List<Folder> folders = Arrays.asList(folder1,folder2);
    
        BooleanBuilder builder =new BooleanBuilder();
        
        //mocking
        // 프로그램에서 작성한 실제 ArticleRepository와 달리 가짜로 만들어낸 Mock ArticleRepository 객체는 별다른 동작 기술 없이는 동작할 수 없다.
        // 따라서 테스트 케이스마다 해당 함수 내부에서 이 객체가 어떻게 동작돼야 하는지 명시적으로 작성해줘야한다.(즉 아래 테스트에서 필요한 함수들의ㅣ 동작을 일일히 정해줘야 하는 것)
        when(folderRepository.save(Mockito.any(Folder.class)))
            .thenAnswer(i -> i.getArguments()[0]); //mockito에서 메소드에 전달된 오브젝트를 그대로 리턴하는 법
        given(folderRepository.findAll(builder)).willReturn(folders);
       
        
        //when
        service.add(folder1);
        service.add(folder2);
        
        //then
        List<Folder> findFolder = service.getList(builder); //내가 테스트할 서비스
        
        assertEquals(folders,findFolder);
       
        System.out.println("end");
        
    }
    
    
    @Test
    @DisplayName("update test")
    public void update_test(){
        //given
        UUID uuid = UUID.randomUUID();
        Folder before_folder = new Folder(uuid,"folder1");
        
        //Folder new_folder = new Folder(uuid,"modified folder");
        
        //mocking
        // 프로그램에서 작성한 실제 ArticleRepository와 달리 가짜로 만들어낸 Mock ArticleRepository 객체는 별다른 동작 기술 없이는 동작할 수 없다.
        // 따라서 테스트 케이스마다 해당 함수 내부에서 이 객체가 어떻게 동작돼야 하는지 명시적으로 작성해줘야한다.(즉 아래 테스트에서 필요한 함수들의ㅣ 동작을 일일히 정해줘야 하는 것)
        when(folderRepository.save(Mockito.any(Folder.class)))
            .thenAnswer(i -> i.getArguments()[0]); //mockito에서 메소드에 전달된 오브젝트를 그대로 리턴하는 법
        when(folderRepository.findById(uuid)).thenReturn(Optional.of(before_folder));
        //when(folderMapper.modify(new_folder, before_folder)).thenReturn(new_folder);
        
        //when
        service.add(before_folder); //일단 기존이 될 폴더를 저장
        Optional<Folder> folder = folderRepository.findById(uuid);
        //만약 기존의 객체가 있다면 == 기존의 객체가 맞다면
        if(folder.isPresent()){
            before_folder.setFolderTitle("modified folder");
        }
       
        //then
        assertEquals(before_folder.getFolderId(),uuid); //ID는 변함 없는지
        assertEquals(before_folder.getFolderTitle(),"modified folder"); //수정은 되었는지
      
        System.out.println("end");
        
    }
    
    
    @Test
    @DisplayName("delete test")
    public void delete_test(){
        //given
        UUID uuid = UUID.randomUUID();
        Folder folder = new Folder(uuid,"test add folder");
    
        //mocking
        // 프로그램에서 작성한 실제 ArticleRepository와 달리 가짜로 만들어낸 Mock ArticleRepository 객체는 별다른 동작 기술 없이는 동작할 수 없다.
        // 따라서 테스트 케이스마다 해당 함수 내부에서 이 객체가 어떻게 동작돼야 하는지 명시적으로 작성해줘야한다.
        given(folderRepository.save(any())).willReturn(folder);
        //when(folderRepository.deleteById(any())).thenAnswer(null);
        //given(folderRepository.findById(uuid)).willReturn(Optional.ofNullable(folder));
    
        service.add(folder);
        
        //즉 service.remove를 했을 때 repository단에서 .deleteById()가 호출되었는지 확인함
        service.remove(uuid);
        verify(folderRepository).deleteById(any()); // check that the method was called
    }
    
}
