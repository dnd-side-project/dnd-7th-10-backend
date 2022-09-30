package com.io.linkapp.link.controller.api;


import com.io.linkapp.link.domain.Inquiry;
import com.io.linkapp.link.response.TagResponse;
import com.io.linkapp.link.service.InquiryService;
import com.querydsl.core.BooleanBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Api(value = "Page", tags = {"Page"})
@RequiredArgsConstructor
@Controller
public class PageApi {
    
    private final InquiryService service;
    
    @SneakyThrows
    @ApiOperation("조회")
    @GetMapping("/admin")
    public String get(Model model){
        Inquiry inquiry = Inquiry.builder().inquiryTitle("title")
            .userId(UUID.randomUUID())
            .inquiry("inquiry")
            .answerTitle("title")
            .answer("answer")
            .isAnswered(false)
            .build();
    
        Inquiry inquiry2 = Inquiry.builder().inquiryTitle("title2")
            .userId(UUID.randomUUID())
            .inquiry("inquiry2")
            .answerTitle("title2")
            .answer("answer2")
            .isAnswered(false)
            .build();
        
        Inquiry newInquiry = service.add(inquiry);
        Inquiry newInquiry2 = service.add(inquiry2);
        
        BooleanBuilder builder = new BooleanBuilder();
        List<Inquiry> inquiryList = service.getList(builder);
        
        model.addAttribute("inquiryList",inquiryList);
        
        return "/admin";
    }

}
