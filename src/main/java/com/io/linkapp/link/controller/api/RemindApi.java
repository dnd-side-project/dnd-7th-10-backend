package com.io.linkapp.link.controller.api;


import com.io.linkapp.config.security.auth.PrincipalDetails;
import com.io.linkapp.link.controller.mapper.RemindFormMapper;
import com.io.linkapp.link.controller.predicate.RemindFormPredicate;
import com.io.linkapp.link.request.RemindRequest;
import com.io.linkapp.link.response.RemindResponse.GetAll;
import com.io.linkapp.link.response.SuccessResponse;
import com.io.linkapp.link.service.RemindService;
import com.io.linkapp.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Remind", tags = {"Remind"})
@RequestMapping(value = "/remind", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class RemindApi {

    private final RemindService service;
    private final UserService userService; //확인용
    
    private final RemindFormMapper formMapper;
    
    @SneakyThrows
    @ApiOperation("목록 조회")
    @GetMapping
    public List<GetAll> getList(RemindRequest.GetAll in,@AuthenticationPrincipal PrincipalDetails principalDetails){
        return formMapper.toGetAllList(service.getList(RemindFormPredicate.search(in,principalDetails.getUser())));
    }
    
    
    @SneakyThrows
    @ApiOperation("단건 조회")
    @GetMapping
    public GetAll get(RemindRequest.GetAll in,@AuthenticationPrincipal PrincipalDetails principalDetails){
        return formMapper.toGetAll(service.get(RemindFormPredicate.search(in,principalDetails.getUser())));
    }
    
//    @SneakyThrows
//    @ApiOperation("등록")
//    @PostMapping
//    public RemindResponse.GetAll add(@Valid @RequestBody RemindRequest.Add in){
//
//        Remind remind = service.add(formMapper.toRemind(in));
//
//        return formMapper.toGetAll(remind);
//    }
//
    @SneakyThrows
    @ApiOperation("삭제")
    @DeleteMapping("/{remindId}")
    public SuccessResponse remove(@PathVariable UUID remindId) {
        return service.remove(remindId);
    }

    
    
    
}
