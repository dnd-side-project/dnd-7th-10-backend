package com.io.linkapp.link.response;

import com.io.linkapp.link.domain.Memo;
import com.io.linkapp.link.domain.OpenGraph;
import com.io.linkapp.link.domain.Tag;
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
    private List<ArticleTagResponse> tags;
    private boolean isBookmark;
    private LocalDateTime registerDate;
    private LocalDateTime modifiedDate;

    public void setBookmark(boolean isBookmark){
        this.isBookmark = isBookmark;
    }
}
