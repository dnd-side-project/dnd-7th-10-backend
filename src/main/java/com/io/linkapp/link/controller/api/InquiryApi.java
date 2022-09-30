package com.io.linkapp.link.controller.api;

import com.io.linkapp.link.controller.mapper.InquiryFormMapper;
import com.io.linkapp.link.controller.predicate.InquiryFormPredicate;
import com.io.linkapp.link.domain.Inquiry;
import com.io.linkapp.link.request.InquiryRequest;
import com.io.linkapp.link.response.InquiryResponse;
import com.io.linkapp.link.response.InquiryResponse.GetAll;
import com.io.linkapp.link.service.InquiryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="Inquiry",tags={"Inquiry"})
@RequestMapping(value="/inquiry",produces= MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class InquiryApi {

    private final InquiryService service;
    private final InquiryFormMapper formMapper;
    
    
    @SneakyThrows
    @ApiOperation("전체 목록 조회")
    @GetMapping
    public List<GetAll> getList(@Valid InquiryRequest.GetAll in){
        return formMapper.toGetAllList(service.getList(InquiryFormPredicate.search(in)));
    }
    
    @SneakyThrows
    @ApiOperation("조회")
    @GetMapping("/{inquiryId}")
    public InquiryResponse.GetAll get(@PathVariable UUID inquiryId){
        return formMapper.toGetAll(service.get(inquiryId));
    }
    
    @SneakyThrows
    @ApiOperation("답변 여부 바꾸기")
    @GetMapping("setAnswered/{inquiryId}")
    public InquiryResponse.GetAll get(@PathVariable UUID inquiryId,Boolean answered){
        return formMapper.toGetAll(service.setIsAnswered(inquiryId,answered));
    }
    
    @SneakyThrows
    @ApiOperation("등록")
    @PostMapping
    public InquiryResponse.GetAll add(@Valid @RequestBody InquiryRequest.Add in){
        //파라미터로 authentication 받아와야 함 - userId 넣어줘야 함
        return formMapper.toGetAll(service.add(formMapper.toInquiry(in)));
    }
    
    @SneakyThrows
    @ApiOperation("수정")
    @PatchMapping("/{inquiryId}")
    public InquiryResponse.GetAll modify(@PathVariable UUID inquiryId,@Valid @RequestBody InquiryRequest.Modify in ){
       return formMapper.toGetAll(service.modify(inquiryId,formMapper.toInquiry(in)));
    }
    
    @ApiOperation("문의 삭제")
    @DeleteMapping("/{inquiryId}")
    public void remove(@PathVariable UUID inquiryId){
         service.remove(inquiryId);
    }
    
}
