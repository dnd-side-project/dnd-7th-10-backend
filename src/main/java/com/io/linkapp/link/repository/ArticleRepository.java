package com.io.linkapp.link.repository;

import com.io.linkapp.link.domain.Article;
import com.io.linkapp.user.domain.User;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ArticleRepository extends JpaRepository<Article, UUID>, QuerydslPredicateExecutor<Article>{

    @EntityGraph(attributePaths = "memos")
    @Query("SELECT DISTINCT article FROM Article article WHERE article.user=:user AND article.isBookmark = true ORDER BY article.registerDate DESC")
    List<Article> findByUser(User user);

    Optional<Article> findById(UUID id);

    @Query("SELECT article FROM Article article LEFT JOIN article.articleTags articleTags LEFT JOIN articleTags.tag where article.id=:uuid")
    Optional<Article> findByIdWithTag(UUID uuid);
}
