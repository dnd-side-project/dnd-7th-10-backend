package com.io.linkapp.link.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleQueryRepository {

    private final JPAQueryFactory queryFactory;

    public ArticleQueryRepository(JPAQueryFactory jpaQueryFactory) {
        this.queryFactory = jpaQueryFactory;
    }
}
