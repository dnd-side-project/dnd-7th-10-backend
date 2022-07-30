package com.io.linkapp.link.controller.predicate;

import com.io.linkapp.link.domain.QTag;
import com.io.linkapp.request.TagRequest;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.apache.commons.lang3.StringUtils;

public class TagFormPredicate {
    
    public static Predicate search(TagRequest.GetAll in){
    
        BooleanBuilder builder = new BooleanBuilder();
        QTag qTag = QTag.tag;
        
        if(StringUtils.isNotEmpty(in.getTagName())){
            builder.and(qTag.tagName.contains(in.getTagName()));
        }
        
        return builder;
    }
    
    
    
}
