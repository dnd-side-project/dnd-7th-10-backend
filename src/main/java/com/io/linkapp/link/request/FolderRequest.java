package com.io.linkapp.link.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class FolderRequest {
    
    @Data
    @Builder
    @ApiModel
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetAll{ //검색 조건
        
        @ApiModelProperty(value = "폴더명")
        private String folderTitle;
        
    }
    
    
    @Data
    @Builder
    @ApiModel
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Add{
    
        @ApiModelProperty(value = "폴더명")
        private String folderTitle;
        
    }
    
    
    @Data
    @Builder
    @ApiModel
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Modify{
    
        @ApiModelProperty(value = "폴더명")
        private String folderTitle;
        
    }
    
    
    
    @Data
    @Builder
    @ApiModel
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Remove {
    
        @ApiModelProperty(value = "폴더명")
        private String folderTitle;
        
    }

}