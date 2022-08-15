package com.io.linkapp.link.response;

import com.io.linkapp.link.domain.Memo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
public class ArticleResponse {
    private UUID id;
    private UUID folderId;
    private UUID remindId;
    private String linkTitle;
    private String linkContent;
    private List<Memo> memos;
    private boolean isPin;
    private LocalDateTime registerDate;
    private LocalDateTime modifiedDate;
}
