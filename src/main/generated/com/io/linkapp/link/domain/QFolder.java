package com.io.linkapp.link.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QFolder is a Querydsl query type for Folder
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QFolder extends EntityPathBase<Folder> {

    private static final long serialVersionUID = 1965957510L;

    public static final QFolder folder = new QFolder("folder");

    public final ComparablePath<java.util.UUID> folderId = createComparable("folderId", java.util.UUID.class);

    public final StringPath folderTitle = createString("folderTitle");

    public QFolder(String variable) {
        super(Folder.class, forVariable(variable));
    }

    public QFolder(Path<? extends Folder> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFolder(PathMetadata metadata) {
        super(Folder.class, metadata);
    }

}

