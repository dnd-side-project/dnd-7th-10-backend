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

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRemind remind = new QRemind("remind");

    public final ListPath<Article, QArticle> articleList = this.<Article, QArticle>createList("articleList", Article.class, QArticle.class, PathInits.DIRECT2);

    public final ComparablePath<java.util.UUID> remindId = createComparable("remindId", java.util.UUID.class);

    public final StringPath remindTitle = createString("remindTitle");

    public final com.io.linkapp.user.domain.QUser user;

    public QRemind(String variable) {
        this(Remind.class, forVariable(variable), INITS);
    }

    public QRemind(Path<? extends Remind> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRemind(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRemind(PathMetadata metadata, PathInits inits) {
        this(Remind.class, metadata, inits);
    }

    public QRemind(Class<? extends Remind> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.io.linkapp.user.domain.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

