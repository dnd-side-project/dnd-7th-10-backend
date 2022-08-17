package com.io.linkapp.link.service;

import com.io.linkapp.exception.CustomGlobalException;
import com.io.linkapp.exception.ErrorCode;
import com.io.linkapp.link.domain.QRemind;
import com.io.linkapp.link.domain.Remind;
import com.io.linkapp.link.repository.RemindRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.util.List;
import java.util.UUID;
import javax.persistence.Id;
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
     * 목록 조회
     *
     * @param search 검색 조건
     * @return 검색된 목록
     */
    public List<Remind> getList(Predicate search){
        return (List<Remind>) repository.findAll(search);
    }
    
    
    /**
     * 리마인드 등록  
     * 사용자 한 명당 하나 이상되는 것을 막아야 함
     * @param remind
     * @return
     */
    public Remind add(Remind remind) {
    
        QRemind qRemind = QRemind.remind;
        UUID userId = remind.getUserId();
        
        List<Remind> list = (List<Remind>) repository.findAll(new BooleanBuilder(qRemind.userId.eq(userId)));
        
        if(list.size()==1){
            throw new CustomGlobalException(ErrorCode.REMIND_NO_MORE);
        }
        
        
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
