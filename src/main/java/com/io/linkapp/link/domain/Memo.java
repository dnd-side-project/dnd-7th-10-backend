package com.io.linkapp.link.domain;

import com.io.linkapp.common.BaseTimeEntity;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

    @Column(name = "article_id")
    private UUID articleId;

    @Column(name = "memo_content")
    private String content;

    @Builder
    public Memo(UUID id, UUID articleId, String content) {
        this.id = id;
        this.articleId = articleId;
        this.content = content;
    }

    public void modify(String content){
        this.content = content;
    }
}
