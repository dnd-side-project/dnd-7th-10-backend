package com.io.linkapp.link.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Tag{
    
    /**
     * 태그 식별번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tag_id",nullable=false)
    private UUID tagId;
    
    @Column(name="tag_name")
    private String tagName;

    private UUID articleId;

    @OneToMany(mappedBy = "tag")
    @JsonManagedReference("tag-article")
    private List<ArticleTag> tagArticles = new ArrayList<>();
}
