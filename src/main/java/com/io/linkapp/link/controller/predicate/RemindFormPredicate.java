package com.io.linkapp.link.controller.predicate;

import com.io.linkapp.link.domain.QRemind;
import com.io.linkapp.link.request.RemindRequest;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.apache.commons.lang3.StringUtils;

public class RemindFormPredicate {
    
    public static Predicate search(RemindRequest.GetAll remindRequest){
        BooleanBuilder builder = new BooleanBuilder();
        QRemind qRemind = QRemind.remind;
        
        if(StringUtils.isNotEmpty(remindRequest.getRemindTitle())){
            builder.and(qRemind.remindTitle.contains(remindRequest.getRemindTitle()));
        }
        
        return builder;
    }
    
}
