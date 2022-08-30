package com.io.linkapp.link.request;

import io.swagger.annotations.ApiModelProperty;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class RemindRequest {
    
    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetAll{ //검색 조건
    
        @ApiModelProperty(value = "해당 유저 식별번호")
        private UUID userId;
        
        @ApiModelProperty(value = "리마인드 타이틀")
        private String remindTitle;
        
    }
    
    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Add{
    
        @ApiModelProperty(value = "해당 유저 식별번호")
        private UUID userId;
    
        @ApiModelProperty(value = "리마인드 타이틀")
        private String remindTitle;
        
    }
    
}
