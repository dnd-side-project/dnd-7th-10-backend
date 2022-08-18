package com.io.linkapp.link.service;

import com.io.linkapp.exception.CustomGlobalException;
import com.io.linkapp.exception.ErrorCode;
import com.io.linkapp.link.domain.QTag;
import com.io.linkapp.link.domain.Tag;
import com.io.linkapp.link.mapper.TagMapper;
import com.io.linkapp.link.repository.TagRepository;
import com.io.linkapp.link.response.SuccessResponse;
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
    
    /**
     * 페이징 조회
     *
     * @param search 검색 조건
     * @return 검색된 목록
     */
    public List<Tag> getList(Predicate search){
        return (List<Tag>)repository.findAll(search);
    }
    
    /**
     * 조회
     *
     * @param id 태그 식별번호
     * @return
     */
    public Tag get(UUID id){
        return repository.findOne(new BooleanBuilder(QTag.tag.tagId.eq(id))).orElseThrow(()->new CustomGlobalException(
            ErrorCode.TAG_NOT_FOUND));
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
     * @param entity 수정본
     * @return
     */
    public Tag modify(UUID id, Tag entity){
    
        if(entity == null){
            return null;
        }
        
        Tag out = repository.findById(id).orElseThrow(()->new CustomGlobalException(ErrorCode.TAG_NOT_FOUND)); // 일단 지금 현재 db에 저장된 정보를 가져옴
        
        out.setTagName(entity.getTagName());
        
        return out;
    }
    
    
    /**
     * 삭제
     *
     * @param id 식별번호
     */
    public SuccessResponse remove(UUID id){
        repository.deleteById(id);

        return SuccessResponse.builder()
                .status(200)
                .message("Tag Remove Success.")
                .build();
    }
    
    
}
