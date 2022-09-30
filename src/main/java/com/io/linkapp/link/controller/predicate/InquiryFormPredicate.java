package com.io.linkapp.link.controller.predicate;

import com.io.linkapp.link.domain.QInquiry;
import com.io.linkapp.link.request.InquiryRequest;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.apache.commons.lang3.StringUtils;

public class InquiryFormPredicate {
    
    public static Predicate search(InquiryRequest.GetAll in){
        BooleanBuilder builder = new BooleanBuilder();
        QInquiry qInquiry = QInquiry.inquiry1;
    
        if(StringUtils.isNotEmpty(in.getInquiryTitle())){
            builder.and(qInquiry.inquiryTitle.contains(in.getInquiryTitle()));
        }
        
        if(StringUtils.isNotEmpty(in.getInquiry())){
            builder.and(qInquiry.inquiry.contains(in.getInquiry()));
        }
        
        return builder;
    }
}
