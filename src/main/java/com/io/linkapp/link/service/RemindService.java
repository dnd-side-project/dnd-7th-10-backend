package com.io.linkapp.link.service;

import com.io.linkapp.link.domain.Remind;
import com.io.linkapp.link.repository.RemindRepository;
import com.querydsl.core.types.Predicate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RemindService {
    
    private final RemindRepository repository;
    
    /**
     * 페이징 조회
     *
     * @param search 검색 조건
     * @param page   페이징 조건
     * @return 검색된 목록
     */
    public Page<Remind> getPage(Predicate search, Pageable page){
        return repository.findAll(search, page);
    }
    
    
    /**
     * 등록
     *
     * @param remind
     * @return
     */
    public Remind add(Remind remind) {
        return repository.save(remind);
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
