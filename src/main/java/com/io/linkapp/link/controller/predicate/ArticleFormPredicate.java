package com.io.linkapp.link.controller.predicate;

import com.io.linkapp.link.domain.QArticle;
import com.io.linkapp.link.request.ArticleRequest;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public class ArticleFormPredicate {

    public static Predicate descriptionSearch(ArticleRequest.OpenGraphSearch descriptionSearchCondition){
        BooleanBuilder builder = new BooleanBuilder();
        QArticle qArticle= QArticle.article;

        builder.and(qArticle.user.eq(descriptionSearchCondition.getUser()));
        builder.and(qArticle.openGraph.linkDescription.contains(descriptionSearchCondition.getOpenGraphTag()));

        return builder;
    }

    public static Predicate titleSearch(ArticleRequest.OpenGraphSearch titleSearchCondition) {
        BooleanBuilder builder = new BooleanBuilder();
        QArticle qArticle= QArticle.article;

        builder.and(qArticle.user.eq(titleSearchCondition.getUser()));
        builder.and(qArticle.openGraph.linkTitle.contains(titleSearchCondition.getOpenGraphTag()));

        return builder;
    }
}
