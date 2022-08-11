package com.io.linkapp.link.response;

import io.swagger.annotations.ApiModelProperty;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class RemindResponse {
    
    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetAll{
    
        @ApiModelProperty(value = "리마인드 식별번호")
        private UUID remindId;
    
    
        @ApiModelProperty(value = "해당 유저 식별번호")
        private UUID userId;
        
        @ApiModelProperty(value = "리마인드 타이틀")
        private String remindTitle;
        
    }
}
