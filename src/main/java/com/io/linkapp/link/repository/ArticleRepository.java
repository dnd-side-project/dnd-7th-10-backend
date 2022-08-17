package com.io.linkapp.link.repository;

import com.io.linkapp.link.domain.Article;
import com.io.linkapp.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepository extends JpaRepository<Article, UUID> {

//    @Query("SELECT article FROM Article article join fetch article.memos")
    List<Article> findByUser(User user);
}
