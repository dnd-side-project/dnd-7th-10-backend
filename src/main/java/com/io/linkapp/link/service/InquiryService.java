package com.io.linkapp.link.service;

import com.io.linkapp.exception.CustomGlobalException;
import com.io.linkapp.exception.ErrorCode;
import com.io.linkapp.link.domain.Inquiry;
import com.io.linkapp.link.repository.InquiryRepository;
import com.querydsl.core.types.Predicate;
import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class InquiryService {
    
    private final InquiryRepository repository;
    
    /**
     * 리스트 조회
     * @param search
     * @return
     */
    public List<Inquiry> getList(Predicate search){
        return (List<Inquiry>) repository.findAll(search);
    }
    
    /**
     * 상세 조회
     * @param id
     * @return
     */
    public Inquiry get(UUID id){
        return repository.findInquiryById(id);
    }
    
    /**
     * (1:1 문의사항) 등록
     * @param inquiry
     * @return
     */
    public Inquiry add(Inquiry inquiry){
        return repository.save(inquiry);
    }
    
    /**
     * 문의 사항 및 답변 수정
     * @param id
     * @param inquiry
     * @return
     */
    public Inquiry modify(UUID id, Inquiry inquiry){
        if(inquiry == null) return null;
        
        Inquiry out = repository.findInquiryById(id);
        
        if(out == null){
            throw new CustomGlobalException(ErrorCode.INQUIRY_NOT_FOUND);
        }else if(out.getIsAnswered()){
            throw new CustomGlobalException(ErrorCode.INQUIRY_ALREADY_ANSWERED);
        }
        
        out.setInquiryTitle(inquiry.getInquiryTitle());
        out.setInquiry(inquiry.getInquiry());
        out.setAnswerTitle(inquiry.getAnswerTitle());
        out.setAnswer(inquiry.getAnswer());
        
        return out;
    }
    
    /**
     * 삭제
     * @param id
     */
    public void remove(UUID id){
        repository.deleteById(id);
    }
    
    

}
