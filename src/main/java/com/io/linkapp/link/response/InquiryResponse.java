package com.io.linkapp.link.response;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class InquiryResponse {
    
    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetAll{
        //해당 문의 식별번호
        private UUID id;
        //질문 제목
        private String inquiryTitle;
        //질문 내용
        private String inquiry;
        //질문 제목
        private String answerTitle;
        //질문 내용
        private String answer;
        //답변 여부
        private boolean isAnswered;
        //등록 날짜
        private LocalDateTime registerDate;
        //수정 날짜
        private LocalDateTime modifiedDate;
    }

}
