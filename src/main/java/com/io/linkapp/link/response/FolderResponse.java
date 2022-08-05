package com.io.linkapp.link.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class FolderResponse {
    
    @Data
    @Builder
    @ApiModel
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetAll{
        
        @ApiModelProperty(value = "폴더 식별번호")
        private UUID folderId;
        
        @ApiModelProperty(value = "폴더명")
        private String folderTitle;
        
    }
    
}
