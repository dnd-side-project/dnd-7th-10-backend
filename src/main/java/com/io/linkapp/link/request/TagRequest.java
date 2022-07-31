package com.io.linkapp.link.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class TagRequest {
    
    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetAll{ //검색 조건
    
        @ApiModelProperty(value = "태그명")
        private String tagName;
        
    }
    
    
    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Add{
        
        @ApiModelProperty(value = "태그명")
        private String tagName;
        
    }
    
    
    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Modify{
        
        @ApiModelProperty(value = "태그명")
        private String tagName;
        
    }
    
    
    
    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Remove {
    
        @ApiModelProperty(value = "태그명")
        private String tagName;
        
    }
    
    
    
    
}
