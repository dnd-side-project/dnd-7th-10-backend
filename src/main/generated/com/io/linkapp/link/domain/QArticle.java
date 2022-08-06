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

    public static final QArticle article = new QArticle("article");

    public final com.io.linkapp.common.QBaseTimeEntity _super = new com.io.linkapp.common.QBaseTimeEntity(this);

    public final ComparablePath<java.util.UUID> folderId = createComparable("folderId", java.util.UUID.class);

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final BooleanPath isMemo = createBoolean("isMemo");

    public final BooleanPath isPin = createBoolean("isPin");

    public final StringPath linkContent = createString("linkContent");

    public final StringPath linkTitle = createString("linkTitle");

    public final ListPath<Memo, QMemo> memos = this.<Memo, QMemo>createList("memos", Memo.class, QMemo.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> registerDate = _super.registerDate;

    public final ComparablePath<java.util.UUID> remindId = createComparable("remindId", java.util.UUID.class);

    public final ComparablePath<java.util.UUID> userId = createComparable("userId", java.util.UUID.class);

    public QArticle(String variable) {
        super(Article.class, forVariable(variable));
    }

    public QArticle(Path<? extends Article> path) {
        super(path.getType(), path.getMetadata());
    }

    public QArticle(PathMetadata metadata) {
        super(Article.class, metadata);
    }

}

