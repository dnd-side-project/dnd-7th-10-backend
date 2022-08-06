package com.io.linkapp.link.service;

import com.io.linkapp.link.domain.Folder;
import com.io.linkapp.link.domain.QFolder;
import com.io.linkapp.link.mapper.FolderMapper;
import com.io.linkapp.link.repository.FolderRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class FolderService {
    
    private final FolderRepository repository;
    
    private final FolderMapper mapper;
    
    
    /**
     * 목록 조회
     *
     * @param search 검색 조건
     * @return 검색된 목록
     */
    public List<Folder> getList(Predicate search){
        return (List<Folder>)repository.findAll(search);
    }
    
    
    /**
     * 페이징 조회
     *
     * @param search 검색 조건
     * @param page   페이징 조건
     * @return 검색된 목록
     */
    public Page<Folder> getPage(Predicate search, Pageable page){
        return repository.findAll(search, page);
    }
    
    /**
     * 조회
     *
     * @param id 식별번호
     * @return
     */
    public Folder get(UUID id){
        return repository.findOne(new BooleanBuilder(QFolder.folder.folderId.eq(id))).orElse(null);
    }
    
    
    /**
     * 등록
     *
     * @param folder
     * @return
     */
    public Folder add(Folder folder) {
        System.out.println("before save folder title :"+folder.getFolderTitle());
        Folder saved = repository.save(folder);
        System.out.println("after save folder title :"+saved.getFolderTitle());
        
        return saved;
    }
    
    /**
     * 수정
     *
     * @param entity
     * @return
     */
    public Folder modify(UUID id, Folder entity){
        entity.setFolderId(id);
        return mapper.modify(entity,get(entity.getFolderId()));
    }
    
    
    /**
     * 삭제
     *
     * @param id 식별번호
     */
    public void remove(UUID id){
        repository.deleteById(id);
    }
    
    
    
    
}
