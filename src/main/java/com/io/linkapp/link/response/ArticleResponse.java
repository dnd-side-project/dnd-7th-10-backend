package com.io.linkapp.link.response;

import com.io.linkapp.link.domain.Memo;
import com.io.linkapp.link.domain.OpenGraph;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ArticleResponse {
    private UUID id;
    private UUID remindId;
    private String linkUrl;
    private OpenGraph openGraph;
    private List<Memo> memos;
    private boolean isBookmark;
    private LocalDateTime registerDate;
    private LocalDateTime modifiedDate;
}
