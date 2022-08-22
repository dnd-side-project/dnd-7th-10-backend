package com.io.linkapp.link.controller.predicate;

import com.io.linkapp.link.domain.QMemo;
import com.io.linkapp.link.request.MemoRequest;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public class MemoFormPredicate {

    public static Predicate search(MemoRequest.SearchMemo in){
        BooleanBuilder builder = new BooleanBuilder();
        QMemo qMemo = QMemo.memo;

        builder.and(qMemo.user.eq(in.getUser()));
        builder.and(qMemo.content.contains(in.getContent()));

        return builder;
    }
}
