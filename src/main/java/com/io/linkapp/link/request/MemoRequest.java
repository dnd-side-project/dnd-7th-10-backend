package com.io.linkapp.link.request;

import java.util.UUID;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemoRequest {
    private UUID articleId;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @Builder
    public MemoRequest(String content, UUID articleId) {
        this.content = content;
        this.articleId = articleId;
    }
}
