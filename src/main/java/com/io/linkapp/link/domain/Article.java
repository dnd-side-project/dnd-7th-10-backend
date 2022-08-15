package com.io.linkapp.link.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.io.linkapp.common.BaseTimeEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Article extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "article_id")
    private UUID id;
    private UUID remindId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id")
    @JsonBackReference(value = "folder-article")
    private Folder folder;

    private String linkTitle;
    private String linkContent;

    @OneToMany(mappedBy = "article")
    @JsonManagedReference("article-memo")
    private List<Memo> memos = new ArrayList<>();

    @OneToMany(mappedBy = "article")
    @JsonManagedReference("article-tag")
    private List<ArticleTag> articleTags = new ArrayList<>();

    private boolean isBookmark = false;

    @Builder
    public Article(Folder folder, UUID remindId, String linkTitle,
        String linkContent, List<ArticleTag> tags) {
        this.folder = folder;
        this.remindId = remindId;
        this.linkTitle = linkTitle;
        this.linkContent = linkContent;
        this.articleTags = tags;
    }

    public void setBookmark(boolean isBookmark){
        this.isBookmark = isBookmark;
    }

    public void addArticleToFolder(Folder folder) {
        this.folder = folder;
        if (!folder.getArticles().contains(this)) {
            folder.getArticles().add(this);
        }
    }
}
