package com.io.linkapp.link.request;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ArticleTagRequest {

    private UUID articleId;
    private UUID tagId;
}
