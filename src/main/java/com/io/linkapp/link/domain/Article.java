package com.io.linkapp.link.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.io.linkapp.common.BaseTimeEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;

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
    private UUID userId;
    private UUID folderId;
    private UUID remindId;

    private String linkTitle;
    private String linkContent;

    @OneToMany(mappedBy = "article")
    @JsonManagedReference
    private List<Memo> memos = new ArrayList<>();

    private boolean isPin;
    private boolean isMemo;

    @Builder
    public Article(UUID userId, UUID folderId, UUID remindId, String linkTitle,
        String linkContent) {
        this.userId = userId;
        this.folderId = folderId;
        this.remindId = remindId;
        this.linkTitle = linkTitle;
        this.linkContent = linkContent;
    }
}
