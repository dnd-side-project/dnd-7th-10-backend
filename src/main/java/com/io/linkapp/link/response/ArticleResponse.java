package com.io.linkapp.link.response;

import com.io.linkapp.link.domain.Article;
import com.io.linkapp.link.domain.ArticleTag;
import com.io.linkapp.link.domain.Memo;
import com.io.linkapp.link.domain.OpenGraph;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Builder
    @Getter
    public static class Tags {
        private UUID id;
        private UUID remindId;
        private String linkUrl;
        private OpenGraph openGraph;
        private List<Memo> memos;
        private List<ArticleTagResponse> tags;
        private boolean isBookmark;
        private LocalDateTime registerDate;
        private LocalDateTime modifiedDate;

        public static List<ArticleResponse.Tags> articleTagBuilder(List<Article> articles){
            List<ArticleResponse.Tags> articleTagList = new ArrayList<>();

            for (Article article : articles) {
                TagsBuilder articleTagResponseBuilder = Tags.builder()
                    .id(article.getId())
                    .remindId(article.getRemindId())
                    .linkUrl(article.getLinkUrl())
                    .openGraph(article.getOpenGraph())
                    .memos(article.getMemos())
                    .registerDate(article.getRegisterDate())
                    .modifiedDate(article.getModifiedDate());

                List<ArticleTag> articleTags = article.getArticleTags();
                List<ArticleTagResponse> tagsResponse = new ArrayList<>();

                for (ArticleTag articleTag : articleTags) {
                    ArticleTagResponse tagResponse = ArticleTagResponse.builder()
                        .tagId(articleTag.getTag().getTagId())
                        .tagName(articleTag.getTag().getTagName())
                        .build();

                    tagsResponse.add(tagResponse);
                }

                Tags tagResponse = articleTagResponseBuilder
                    .tags(tagsResponse)
                    .build();

                articleTagList.add(tagResponse);
            }

            return articleTagList;

        }
    }

    public void setBookmark(boolean isBookmark){
        this.isBookmark = isBookmark;
    }
}
