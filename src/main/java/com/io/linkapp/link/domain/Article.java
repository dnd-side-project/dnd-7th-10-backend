package com.io.linkapp.link.domain;

import com.io.linkapp.common.BaseTimeEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
    private UUID userId;
    private UUID folderId;
    private UUID remindId;

    private String linkTitle;
    private String linkContent;

    @OneToMany(mappedBy = "article")
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
