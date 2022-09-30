package com.io.linkapp.link.request;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class InquiryRequest {
    
    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetAll{ //검색 조건
        //질문 제목
        private String inquiryTitle;
        //질문 내용
        private String inquiry;
    }
    
    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Add{
        //질문 제목
        private String inquiryTitle;
        //질문자 아이디
        private UUID userId;
        //질문 내용
        private String inquiry;
        //질문 제목
        private String answerTitle;
        //질문 내용
        private String answer;
    }
    
    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Modify{
        //질문 제목
        private String inquiryTitle;
        //질문자 아이디
        private UUID userId;
        //질문 내용
        private String inquiry;
        //질문 제목
        private String answerTitle;
        //질문 내용
        private String answer;
    }
    

}
