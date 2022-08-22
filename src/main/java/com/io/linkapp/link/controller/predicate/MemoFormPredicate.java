package com.io.linkapp.link.controller.predicate;

import com.io.linkapp.link.domain.QMemo;
import com.io.linkapp.link.request.MemoRequest;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public class MemoFormPredicate {

    public static Predicate search(MemoRequest.SearchMemo memoContentSearchCondition){
        BooleanBuilder builder = new BooleanBuilder();
        QMemo qMemo = QMemo.memo;

        builder.and(qMemo.user.eq(memoContentSearchCondition.getUser()));
        builder.and(qMemo.content.contains(memoContentSearchCondition.getContent()));
        builder.or(qMemo.content.toUpperCase().contains(memoContentSearchCondition.getContent().toUpperCase()));

        return builder;
    }
}
