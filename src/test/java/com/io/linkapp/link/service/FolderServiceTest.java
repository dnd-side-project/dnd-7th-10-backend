package com.io.linkapp.link.service;

import com.io.linkapp.exception.CustomGlobalException;
import com.io.linkapp.exception.ErrorCode;
import com.io.linkapp.link.domain.Folder;
import com.io.linkapp.link.repository.FolderRepository;
import com.io.linkapp.link.request.FolderRequest;
import com.io.linkapp.link.response.FolderResponse;
import com.io.linkapp.user.domain.User;
import com.io.linkapp.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class FolderServiceTest {

    @Autowired
    FolderRepository folderRepository;
    @Autowired
    FolderService folderService;
    @Autowired
    UserRepository userRepository;

    Folder folder;
    User user;

    @BeforeEach
    public void setUp(){
        folder = Folder.builder()
                .folderTitle("testFolder")
                .folderColor("red")
                .build();

        folder = folderRepository.save(folder);

        user = User.builder()
                .username("testUser")
                .password("testPw")
                .build();

        user = userRepository.save(user);
    }

    @Test
    public void get_메소드는_UUID로_조회한다(){
        FolderResponse.GetArticles folderResponse = folderService.get(folder.getFolderId());

        assertThat(folderResponse.getFolderId()).isEqualTo(folder.getFolderId());
        assertThat(folderResponse.getFolderColor()).isEqualTo("red");
        assertThat(folderResponse.getFolderTitle()).isEqualTo("testFolder");
    }

    @Test
    public void get_메소드가_NULL을_조회시_CustomException을_반환한다(){
        assertThatThrownBy(() -> folderService.get(UUID.randomUUID()))
                .isInstanceOf(CustomGlobalException.class)
                .hasMessageContaining(ErrorCode.FOLDER_NOT_FOUND.getMessage())
                .extracting("status")
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void add_메소드로_추가한다(){
        FolderResponse savedFolder = folderService.add(FolderRequest.builder()
                .folderTitle("testFolder")
                .folderColor("blue")
                .build(), user);

        assertThat(savedFolder.getFolderTitle()).isEqualTo("testFolder");
        assertThat(savedFolder.getFolderColor()).isEqualTo("blue");
        assertThat(folderRepository.findById(savedFolder.getFolderId())).isNotNull();
    }

    @Test
    public void remove_메소드에서_예외(){

    }
}