package com.io.linkapp.link.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QOpenGraph is a Querydsl query type for OpenGraph
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QOpenGraph extends BeanPath<OpenGraph> {

    private static final long serialVersionUID = -1632121588L;

    public static final QOpenGraph openGraph = new QOpenGraph("openGraph");

    public final StringPath linkDescription = createString("linkDescription");

    public final StringPath linkImage = createString("linkImage");

    public final StringPath linkTitle = createString("linkTitle");

    public QOpenGraph(String variable) {
        super(OpenGraph.class, forVariable(variable));
    }

    public QOpenGraph(Path<? extends OpenGraph> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOpenGraph(PathMetadata metadata) {
        super(OpenGraph.class, metadata);
    }

}

