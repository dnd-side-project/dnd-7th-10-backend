package com.io.linkapp.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -2001575628L;

    public static final QUser user = new QUser("user");

    public final com.io.linkapp.common.QBaseTimeEntity _super = new com.io.linkapp.common.QBaseTimeEntity(this);

    public final ListPath<com.io.linkapp.link.domain.Article, com.io.linkapp.link.domain.QArticle> articles = this.<com.io.linkapp.link.domain.Article, com.io.linkapp.link.domain.QArticle>createList("articles", com.io.linkapp.link.domain.Article.class, com.io.linkapp.link.domain.QArticle.class, PathInits.DIRECT2);

    public final ListPath<com.io.linkapp.link.domain.Folder, com.io.linkapp.link.domain.QFolder> folders = this.<com.io.linkapp.link.domain.Folder, com.io.linkapp.link.domain.QFolder>createList("folders", com.io.linkapp.link.domain.Folder.class, com.io.linkapp.link.domain.QFolder.class, PathInits.DIRECT2);

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final ListPath<com.io.linkapp.link.domain.Memo, com.io.linkapp.link.domain.QMemo> memos = this.<com.io.linkapp.link.domain.Memo, com.io.linkapp.link.domain.QMemo>createList("memos", com.io.linkapp.link.domain.Memo.class, com.io.linkapp.link.domain.QMemo.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath password = createString("password");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> registerDate = _super.registerDate;

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public final StringPath username = createString("username");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

