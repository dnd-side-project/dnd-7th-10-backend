package com.io.linkapp.link.controller.predicate;

import com.io.linkapp.link.domain.QMemo;
import com.io.linkapp.link.request.MemoRequest;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.util.ObjectUtils;

public class MemoFormPredicate {

    public static Predicate search(MemoRequest.Search memoContentSearchCondition){
        BooleanBuilder builder = new BooleanBuilder();
        QMemo qMemo = QMemo.memo;
        builder.and(qMemo.user.eq(memoContentSearchCondition.getUser()));

        if(!ObjectUtils.isEmpty(memoContentSearchCondition.getContent())) {
            builder.and(qMemo.content.toUpperCase().contains(memoContentSearchCondition.getContent().toUpperCase()));
        }

        return builder;
    }
}
