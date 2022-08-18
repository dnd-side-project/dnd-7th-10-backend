package com.io.linkapp.link.response;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ArticleTagResponse {
    private UUID tagId;
    private String tagName;
}
