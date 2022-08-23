package com.io.linkapp.link.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRemind is a Querydsl query type for Remind
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRemind extends EntityPathBase<Remind> {

    private static final long serialVersionUID = -1994660323L;

    public static final QRemind remind = new QRemind("remind");

    public final ListPath<Article, QArticle> articleList = this.<Article, QArticle>createList("articleList", Article.class, QArticle.class, PathInits.DIRECT2);

    public final StringPath cron = createString("cron");

    public final ComparablePath<java.util.UUID> remindId = createComparable("remindId", java.util.UUID.class);

    public final StringPath remindTitle = createString("remindTitle");

    public final ComparablePath<java.util.UUID> userId = createComparable("userId", java.util.UUID.class);

    public QRemind(String variable) {
        super(Remind.class, forVariable(variable));
    }

    public QRemind(Path<? extends Remind> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRemind(PathMetadata metadata) {
        super(Remind.class, metadata);
    }

}

