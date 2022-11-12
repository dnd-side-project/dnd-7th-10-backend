package com.io.linkapp.link.controller.api;

import com.io.linkapp.config.quartz.QuartzService;
import com.io.linkapp.config.security.auth.PrincipalDetails;
import com.io.linkapp.link.controller.mapper.RemindFormMapper;
import com.io.linkapp.link.domain.Remind;
import com.io.linkapp.link.request.PushRequest;
import com.io.linkapp.link.response.RemindResponse.GetAll;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Quartz", tags = {"Quartz"})
@RequiredArgsConstructor
@RestController
public class SchedulerApi {

    private final QuartzService service;
    private final RemindFormMapper formMapper;
    
    
    @SneakyThrows
    @ApiOperation("알림 스케줄러 설정")
    @PostMapping("/quartz")
    public GetAll testQuartzForReal(@RequestBody PushRequest pushRequest,@AuthenticationPrincipal PrincipalDetails principalDetails){ //여기서 사용자 정보 Auth객체로 받고
        Remind remind = service.initSchedule(pushRequest, principalDetails.getUser()); //여기로 넘겨준 다음에
        return formMapper.toGetAll(remind);
    }
    
    
    @SneakyThrows
    @ApiOperation("현재 유저의 모든 알람 정보 삭제")
    @DeleteMapping("/clear")
    public void clear(@AuthenticationPrincipal PrincipalDetails principalDetails){
        service.resetScheduler(principalDetails.getUser());
        
    }
}
