package com.io.linkapp.link.service;

import com.io.linkapp.exception.CustomGlobalException;
import com.io.linkapp.exception.ErrorCode;
import com.io.linkapp.link.domain.Article;
import com.io.linkapp.link.domain.Parent;
import com.io.linkapp.link.domain.Remind;
import com.io.linkapp.link.repository.ParentRepository;
import com.io.linkapp.link.repository.RemindRepository;
import com.querydsl.core.types.Predicate;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ParentService {
    
    private final ParentRepository repository;
    
  
    public List<Parent> getPage(){
        return repository.findAll();
    }
    
    
    public Parent add(Parent parent) {
        
        return repository.save(parent);
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
