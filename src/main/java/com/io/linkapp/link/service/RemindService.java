package com.io.linkapp.link.service;

import com.io.linkapp.link.domain.Remind;
import com.io.linkapp.link.repository.RemindRepository;
import com.io.linkapp.link.response.SuccessResponse;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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
        
        List<Remind> list = (List<Remind>) repository.findAll(search);
        
        //리마인드가 아티클 객체들을 잘 가지고 왔는지 확인
//        for(int i=0;i<list.size();i++){
//            //System.out.println(list.get(i).getArticleList().size());
//            for(int j=0;j<list.get(i).getArticleList().size();j++){
//                System.out.println(list.get(i).getArticleList().get(j).getLinkUrl());
//            }
//        }
        return list;
    }
    
    
    /**
     * 리마인드 등록  
     * 사용자 한 명당 하나 이상되는 것을 막아야 함 - 사용자의 dummy remind 는 따로 있도록 해줌
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
    public SuccessResponse remove(UUID id){
        repository.deleteById(id);

        return SuccessResponse.builder()
                .status(200)
                .message("Remind Remove Success.")
                .build();
    }

}
