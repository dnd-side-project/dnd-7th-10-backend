package com.io.linkapp.link.service;

import com.io.linkapp.link.domain.QTag;
import com.io.linkapp.link.domain.Tag;
import com.io.linkapp.link.mapper.TagMapper;
import com.io.linkapp.link.repository.TagRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TagService {

    private final TagRepository repository;
    private final TagMapper mapper;
    
    /**
     * 목록 조회
     *
     * @param search 검색 조건
     * @return 검색된 목록
     */
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
    public List<Tag> getList(Predicate search){
        return (List<Tag>)repository.findAll(search);
    }
    
    /**
     * 페이징 조회
     *
     * @param search 검색 조건
     * @param page   페이징 조건
     * @return 검색된 목록
     */
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
    public Page<Tag> getPage(Predicate search, Pageable page){
        return repository.findAll(search, page);
    }
    
    /**
     * 조회
     *
     * @param id 식별번호
     * @return
     */
    @Transactional(readOnly = true)
    public Tag get(UUID id){
        return repository.findOne(new BooleanBuilder(QTag.tag.tagId.eq(id))).orElse(null);
    }
    
    /**
     * 등록
     *
     * @param tag
     * @return
     */
    public Tag add(Tag tag) {
        return repository.save(tag);
    }
    
    /**
     * 수정
     *
     * @param entity
     * @return
     */
    public Tag modify(UUID id, Tag entity){
        entity.setTagId(id);
        return mapper.modify(entity,get(entity.getTagId()));
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
