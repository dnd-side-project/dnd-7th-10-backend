package com.io.linkapp.response;

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
        
        private UUID tagId;
        
        private UUID articleId;
        
        private String tagName;
        
    }
    
}
