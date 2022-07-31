package com.io.linkapp.link.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@ToString
public class MemoRequest {

    private UUID id;
    private UUID articleId;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
    private LocalDateTime registerDate;
    private LocalDateTime modifiedDate;


    //TODO : @param articleId : Will delete After make article entity
    @Builder
    public MemoRequest(String content, UUID articleId) {
        this.content = content;
        this.articleId = articleId;
    }
}
