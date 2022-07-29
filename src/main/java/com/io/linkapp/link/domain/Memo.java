package com.io.linkapp.link.domain;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Memo {

    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "memo_id")
    private UUID id;

    @Column(name = "article_id")
    private UUID articleId;

    @Column(name = "memo_content")
    private String content;

    @CreatedDate
    private LocalDateTime registerDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @Builder
    public Memo(UUID id, UUID articleId, String content, LocalDateTime registerDate,
        LocalDateTime modifiedDate) {
        this.id = id;
        this.articleId = articleId;
        this.content = content;
        this.registerDate = registerDate;
        this.modifiedDate = modifiedDate;
    }

    public void edit(String content){
        this.content = content;
    }
}
