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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
     * 페이징 조회
     *
     * @param search 검색 조건
     * @param page   페이징 조건
     * @return 검색된 목록
     */
    public Page<Inquiry> getPage(Predicate search, Pageable page) {
        return repository.findAll(search, page);
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
        out.setAnswered(inquiry.getIsAnswered());
        
        repository.save(out);
        
        return out;
    }
    
    /**
     * (1:1 문의사항) 답변 여부
     * @param
     * @return
     */
    public Inquiry setIsAnswered(UUID id, Boolean answered){
        Inquiry inquiry = repository.getById(id);
        inquiry.setAnswered(answered);
        return repository.save(inquiry);
    }
    
    /**
     * 삭제
     * @param id
     */
    public void remove(UUID id){
        repository.deleteById(id);
    }
    
    public void set( ){
        Inquiry inquiry = Inquiry.builder().inquiryTitle("프로젝트 주제는 어떻게 정해지나요?")
            .userId(UUID.fromString("d6900636-1d12-4ac9-ab05-f02005cc5b64"))
            .inquiry("프로젝트 주제는 어떻게 정해지나요? 궁금합니다.")
            .answerTitle("프로젝트 주제에 대한 답변입니다")
            .answer(" 운영진들이 팀을 이루어 드립니다. 정해진 팀원들끼리 상의를 통해서 프로젝트의 주제를 정하게 됩니다. 미리 생각해놓으면 좋을 것 같네요 :)")
            .isAnswered(true)
            .build();
        
        Inquiry inquiry2 = Inquiry.builder().inquiryTitle("프로젝트에 사용 할 기술 스택은 어떻게 하나요?")
            .userId(UUID.fromString("d6900636-1d12-4ac9-ab05-f02005cc5b64"))
            .inquiry("프로젝트 기술 선택 선정에 대해 문의드리고 싶습니다.")
            .answerTitle("프로젝트 기술 선택에 대한 답변 드립니다.")
            .answer("프로젝트 참여 시 사용하실 스택은 향후 구성될 팀원들과 논의하여 결정하게됩니다. 대부분 입문 단계의 실력으로 프로젝트에 참가 하고 계시며, 본인이 맡은 역할을 수행하기 위해 프로젝트 기간동안 스스로 열심히 공부하고 공부한 내용을 프로젝트에 적용해나갑니다. 지식이 완전 제로인 상태가 아니시면, 굳센 마음가짐과 노력을 가지고 참가하시면 됩니다.")
            .isAnswered(true)
            .build();
        
        Inquiry inquiry3 = Inquiry.builder().inquiryTitle("한 팀에 몇 명 정도 인가요?")
            .userId(UUID.fromString("d6900636-1d12-4ac9-ab05-f02005cc5b64"))
            .inquiry("한 팀에 몇 명 정도로 구성되나요?")
            .answerTitle("팀 구성에 대한 답변입니다.")
            .answer("개발자 4명(프론트 2명, 백 2명)에 디자이너 2명을 기본으로 하고 있습니다")
            .isAnswered(true)
            .build();
        
        Inquiry inquiry4 = Inquiry.builder().inquiryTitle("합격자 발표는 언제인가요?")
            .userId(UUID.fromString("d6900636-1d12-4ac9-ab05-f02005cc5b64"))
            .inquiry("합격자 발표날에 대해 궁금합니다")
            .answerTitle("합격자 발표일에 대한 답변입니다")
            .answer("7기 합격자 발표는 2022년 7월 1일 입니다.")
            .isAnswered(true)
            .build();
    
        Inquiry inquiry5 = Inquiry.builder().inquiryTitle("답변 꼭 부탁드립니다")
            .userId(UUID.fromString("d6900636-1d12-4ac9-ab05-f02005cc5b64"))
            .inquiry("모임에 대한 장소는 어떻게 하나요?")
            .isAnswered(false)
            .build();
    
        Inquiry inquiry6 = Inquiry.builder().inquiryTitle("답변이 필요합니다")
            .userId(UUID.fromString("d6900636-1d12-4ac9-ab05-f02005cc5b64"))
            .inquiry("답변 달아주지 마세여 ㅎㅎ")
            .isAnswered(false)
            .build();
        
        Inquiry newInquiry = add(inquiry);
        Inquiry newInquiry2 = add(inquiry2);
        Inquiry newInquiry3 = add(inquiry3);
        Inquiry newInquiry4 = add(inquiry4);
        Inquiry newInquiry5 = add(inquiry5);
        Inquiry newInquiry6 = add(inquiry6);
        
        
    }

}
