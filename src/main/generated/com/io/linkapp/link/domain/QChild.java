package com.io.linkapp.link.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QChild is a Querydsl query type for Child
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QChild extends EntityPathBase<Child> {

    private static final long serialVersionUID = -493753084L;

    public static final QChild child = new QChild("child");

    public final StringPath childName = createString("childName");

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final BooleanPath isBookmark = createBoolean("isBookmark");

    public final ComparablePath<java.util.UUID> parentId = createComparable("parentId", java.util.UUID.class);

    public QChild(String variable) {
        super(Child.class, forVariable(variable));
    }

    public QChild(Path<? extends Child> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChild(PathMetadata metadata) {
        super(Child.class, metadata);
    }

}

