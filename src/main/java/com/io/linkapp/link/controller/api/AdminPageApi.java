package com.io.linkapp.link.controller.api;


import com.io.linkapp.link.domain.Inquiry;
import com.io.linkapp.link.service.InquiryService;
import com.querydsl.core.BooleanBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Api(value = "AdminPage", tags = {"AdminPage"})
@RequestMapping(value = "/admin/inquiry")
@Controller
@RequiredArgsConstructor
public class AdminPageApi {
    
    private final InquiryService service;
    private int cnt =0;
    
    @SneakyThrows
    @ApiOperation("페이징 세팅")
    @PostMapping("/set")
    @ResponseBody
    public void set( ){
        service.set();

    }
    
    
    
    @SneakyThrows
    @ApiOperation("전체 페이징 조회")
    @GetMapping
    public String get(Model model, @PageableDefault(size = 3, sort = "registerDate", direction = Sort.Direction.DESC) Pageable page){
        
//        cnt+=1;
//
//        if(cnt == 1 ){
//            service.set();
//        }
//
        BooleanBuilder builder = new BooleanBuilder();
        Page<Inquiry> list = service.getPage(builder,page);
    
        //페이지블럭 처리
        //1을 더해주는 이유는 pageable은 0부터라 1을 처리하려면 1을 더해서 시작해주어야 한다.
        int nowPage = list.getPageable().getPageNumber() + 1;
        //-1값이 들어가는 것을 막기 위해서 max값으로 두 개의 값을 넣고 더 큰 값을 넣어주게 된다.
        int startPage =  Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage+9, list.getTotalPages());
        
        model.addAttribute("inquiryList",list);
        model.addAttribute("pageable",list.getPageable());
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
    
        return "/admin";
    }
    
    
    @SneakyThrows
    @ApiOperation("페이징 상세 조회")
    @GetMapping("/{id}")
    public String detail(Model model,@PathVariable UUID id){
        Inquiry inquiry = service.get(id);
        model.addAttribute("detailInfo",inquiry);
        return "/detail";
    }
    
    
    @SneakyThrows
    @ApiOperation("답변 등록")
    @PostMapping(value = "/answer/{id}")
    public String answer(Model model,@PathVariable UUID id, @RequestParam Map<String,String> answerData){
        
        Inquiry origin = service.get(id);
        
        
        Inquiry inquiry = Inquiry.builder().inquiryTitle(origin.getInquiryTitle())
            .inquiry(origin.getInquiry())
            .answerTitle(answerData.get("title"))
            .answer(answerData.get("answer"))
            .isAnswered(true)
            .build();
        
        service.modify(id,inquiry);
        
        return String.format("redirect:/admin/inquiry/%s", id);
    }
    
    
    
    

}
