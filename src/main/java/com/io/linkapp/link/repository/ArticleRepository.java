package com.io.linkapp.link.repository;

import com.io.linkapp.link.domain.Article;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, UUID> {

}
