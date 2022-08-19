package com.io.linkapp.link.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QMarket is a Querydsl query type for Market
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMarket extends EntityPathBase<Market> {

    private static final long serialVersionUID = -2141349548L;

    public static final QMarket market = new QMarket("market");

    public final ComparablePath<java.util.UUID> marketId = createComparable("marketId", java.util.UUID.class);

    public final StringPath marketName = createString("marketName");

    public final StringPath marketPrice = createString("marketPrice");

    public QMarket(String variable) {
        super(Market.class, forVariable(variable));
    }

    public QMarket(Path<? extends Market> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMarket(PathMetadata metadata) {
        super(Market.class, metadata);
    }

}

