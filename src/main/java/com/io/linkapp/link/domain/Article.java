package com.io.linkapp.link.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.io.linkapp.common.BaseTimeEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.io.linkapp.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Getter
@NoArgsConstructor
@Entity
public class Article extends BaseTimeEntity implements Comparable<Article> {

    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "article_id")
    private UUID id;
    
    @Column(name = "remind_id")
    private UUID remindId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id")
    @JsonBackReference(value = "folder-article")
    private Folder folder;

    private String linkUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "user-article")
    private User user;

    @Embedded
    private OpenGraph openGraph;

    @OneToMany(mappedBy = "article")
    @JsonManagedReference("article-memo")
    private List<Memo> memos = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    @JsonManagedReference("article-tag")
    private List<ArticleTag> articleTags = new ArrayList<>();

    private boolean isBookmark = false;

    @Builder
    public Article(Folder folder, UUID remindId, String linkUrl,
        OpenGraph openGraph, List<ArticleTag> tags, User user) {
        this.folder = folder;
        this.remindId = remindId;
        this.linkUrl = linkUrl;
        this.openGraph = openGraph;
        this.articleTags = tags;
        this.user = user;
    }

    public void setBookmark(boolean isBookmark){
        this.isBookmark = isBookmark;
    }
    
    public void setRemindId(UUID id){
        this.remindId = id;
    }

    public void modifyArticle(Folder folder, List<ArticleTag> articleTags) {
        this.folder = folder;
        this.articleTags = articleTags;
    }

    @Override
    public int compareTo(@NotNull Article article) {
        if(this.getRegisterDate().isBefore(article.getRegisterDate())){
            return 1;
        } else if (this.getRegisterDate().isAfter(article.getRegisterDate())) {
            return -1;
        }
        return 0;
    }
}
