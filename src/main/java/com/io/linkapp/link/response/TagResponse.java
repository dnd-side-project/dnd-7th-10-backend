package com.io.linkapp.link.response;

import io.swagger.annotations.ApiModelProperty;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class TagResponse {
    
    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetAll{
    
        @ApiModelProperty(value = "태그 식별번호")
        private UUID tagId;
        
        @ApiModelProperty(value = "태그명")
        private String tagName;
        
    }
    
}
