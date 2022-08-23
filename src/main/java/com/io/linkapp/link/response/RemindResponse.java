package com.io.linkapp.link.response;

import com.io.linkapp.link.domain.Article;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
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
    
        @ApiModelProperty(value = "cron 정보")
        private String cron;
        
        @ApiModelProperty(value = "리마인드 타이틀")
        private String remindTitle;
    
        @ApiModelProperty(value = "해당 리마인드의 아티클 리스트")
        private List<Article> articleList;
        
    }
}
