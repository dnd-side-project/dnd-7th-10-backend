package com.io.linkapp.link.repository;

import com.io.linkapp.link.domain.ArticleTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ArticleTagRepository extends JpaRepository<ArticleTag, Long> {

    @Query("SELECT articleTag FROM ArticleTag articleTag WHERE articleTag.article.id=:articleId AND articleTag.tag.tagId=:tagId")
    ArticleTag findByArticleAndTag(@Param("articleId") UUID articleId, @Param("tagId") UUID tagId);
}
