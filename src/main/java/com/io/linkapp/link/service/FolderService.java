package com.io.linkapp.link.service;

import com.io.linkapp.link.domain.Folder;
import com.io.linkapp.link.domain.QFolder;
import com.io.linkapp.link.mapper.FolderMapper;
import com.io.linkapp.link.repository.FolderRepository;
import com.io.linkapp.link.request.FolderRequest;
import com.io.linkapp.link.response.FolderResponse;
import com.io.linkapp.user.domain.User;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;

    /**
     * 목록 조회
     *
     * @param search 검색 조건
     * @return 검색된 목록
     */
    public List<Folder> getList(Predicate search){
        return (List<Folder>) folderRepository.findAll(search);
    }


    /**
     * 페이징 조회
     *
     * @param search 검색 조건
     * @param page   페이징 조건
     * @return 검색된 목록
     */
    public Page<Folder> getPage(Predicate search, Pageable page){
        return folderRepository.findAll(search, page);
    }

    /**
     * 조회
     *
     * @param id 식별번호
     * @return
     */
    public Folder get(UUID id){
        return folderRepository.findOne(new BooleanBuilder(QFolder.folder.folderId.eq(id))).orElse(null);
    }

    public FolderResponse add(FolderRequest folderRequest, User user) {
        folderRequest.setUser(user);
        Folder folder = FolderMapper.INSTANCE.toEntity(folderRequest);
        return FolderMapper.INSTANCE.toResponseDto(folderRepository.save(folder));
    }

    /**
     * 수정
     *
     * @param entity
     * @return
     */


    /**
     * 삭제
     *
     * @param id 식별번호
     */
    public void remove(UUID id){
        folderRepository.deleteById(id);
    }




}
