package com.io.linkapp.link.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArticle is a Querydsl query type for Article
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QArticle extends EntityPathBase<Article> {

    private static final long serialVersionUID = 766012126L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QArticle article = new QArticle("article");

    public final com.io.linkapp.common.QBaseTimeEntity _super = new com.io.linkapp.common.QBaseTimeEntity(this);

    public final ListPath<ArticleTag, QArticleTag> articleTags = this.<ArticleTag, QArticleTag>createList("articleTags", ArticleTag.class, QArticleTag.class, PathInits.DIRECT2);

    public final QFolder folder;

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final BooleanPath isBookmark = createBoolean("isBookmark");

    public final StringPath linkUrl = createString("linkUrl");

    public final ListPath<Memo, QMemo> memos = this.<Memo, QMemo>createList("memos", Memo.class, QMemo.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final QOpenGraph openGraph;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> registerDate = _super.registerDate;

    public final ComparablePath<java.util.UUID> remindId = createComparable("remindId", java.util.UUID.class);

    public final com.io.linkapp.user.domain.QUser user;

    public QArticle(String variable) {
        this(Article.class, forVariable(variable), INITS);
    }

    public QArticle(Path<? extends Article> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QArticle(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QArticle(PathMetadata metadata, PathInits inits) {
        this(Article.class, metadata, inits);
    }

    public QArticle(Class<? extends Article> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.folder = inits.isInitialized("folder") ? new QFolder(forProperty("folder"), inits.get("folder")) : null;
        this.openGraph = inits.isInitialized("openGraph") ? new QOpenGraph(forProperty("openGraph")) : null;
        this.user = inits.isInitialized("user") ? new com.io.linkapp.user.domain.QUser(forProperty("user")) : null;
    }

}

