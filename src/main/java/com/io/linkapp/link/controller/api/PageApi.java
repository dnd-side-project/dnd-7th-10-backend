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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Api(value = "Page", tags = {"Page"})
@RequiredArgsConstructor
@Controller
public class PageApi {
    
    private final InquiryService service;
    private int cnt =0;
    
    @SneakyThrows
    @ApiOperation("페이징 세팅")
    @PostMapping("/admin/set")
    @ResponseBody
    public void set( ){
        service.set();

    }
    
    
    
    @SneakyThrows
    @ApiOperation("페이징 조회")
    @GetMapping("/admin")
    public String get(Model model, @PageableDefault(size = 3, sort = "registerDate", direction = Sort.Direction.DESC) Pageable page){
        
        cnt+=1;
        
        if(cnt == 1 ){
            service.set();
        }
        
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

}
