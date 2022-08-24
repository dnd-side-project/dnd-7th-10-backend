package com.io.linkapp.link.controller.predicate;

import com.io.linkapp.link.domain.QRemind;
import com.io.linkapp.link.request.RemindRequest;
import com.io.linkapp.user.domain.User;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;

public class RemindFormPredicate {
    
    public static Predicate search(RemindRequest.GetAll remindRequest, User user){
        BooleanBuilder builder = new BooleanBuilder();
        QRemind qRemind = QRemind.remind;
        
        UUID userId = user.getId();
        
        if(StringUtils.isNotEmpty(remindRequest.getRemindTitle())){
            builder.and(qRemind.remindTitle.contains(remindRequest.getRemindTitle()));
        }
        
        builder.and(qRemind.userId.eq(userId));
        
        return builder;
    }
    
}
