package com.io.linkapp.link.service;

import com.io.linkapp.link.domain.Child;
import com.io.linkapp.link.repository.ChildRepository;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;


@Service
@Transactional
@RequiredArgsConstructor
public class ChildService {
    
    private final ChildRepository repository;
    
    
    public List<Child> getPage(){
        return  repository.findAll();
    }
    
    
    public Optional<Child> get(UUID id) {
        
        return repository.findById(id);
    }
    
    public Child add(Child child) {
        
        return repository.save(child);
    }
    
    /**
     * 
     * 어이없이 헤맸던 것.. setter를 통해 엔티티를 조작하려면 당연히 서비스 단에서 해줘야 함
     * 그래야 진짜 원본인 repository에서 바로 나온 것을 조작할 수 있기 때문.
     * service.get()을 통해 api에서 setter를 했더니 안 먹혔음
     */
    
    public Child doBookmark(String Id){
        
        Child child =repository.findById(UUID.fromString(Id)).orElse(null);
        child.setBookmark(true);
        child.setParentId(UUID.fromString("467d22c0-bd1e-48d4-ba7f-e7e3187fc21f")); //그럼 리마인드 아이디를 가지게 됨
        return child;
    }
    
    
    public Child undoBookmark(String Id){
        
        Child child =repository.findById(UUID.fromString(Id)).orElse(null);
        child.setBookmark(false);
        child.setParentId(UUID.fromString( "00000000-0000-0000-0000-000000000000")); //그럼 리마인드 아이디를 가지지 않게 됨
        return child;
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
