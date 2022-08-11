package com.io.linkapp.link.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.io.linkapp.common.BaseTimeEntity;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@Entity
public class Memo extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "memo_id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    @JsonBackReference(value = "article-memo")
    private Article article;

    @Column(name = "memo_content")
    private String content;

    @Builder
    public Memo(UUID id, Article article, String content) {
        this.id = id;
        this.article = article;
        this.content = content;
    }

    public void modify(String content){
        this.content = content;
    }

    public void addMemoToArticle(Article article) {
        this.article = article;
        if(!article.getMemos().contains(this)) {
            article.getMemos().add(this);
        }
    }
}
