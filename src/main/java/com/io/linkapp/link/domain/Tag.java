package com.io.linkapp.link.domain;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tag")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@ToString(callSuper = true)
@EqualsAndHashCode //(callSuper = true)
public class Tag {
    
    /**
     * 태그 식별번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tag_id",nullable=false)
    private UUID tagId;
    
    
    @Column(name="article_id",nullable = false)
    private UUID articleId;
    
    @Column(name="tag_name")
    private String tagName;
    
    
}
