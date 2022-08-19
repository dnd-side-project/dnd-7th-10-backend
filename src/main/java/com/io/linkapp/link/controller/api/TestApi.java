package com.io.linkapp.link.controller.api;

import com.io.linkapp.config.quartz.QuartzService;
import com.io.linkapp.link.controller.predicate.TagFormPredicate;
import com.io.linkapp.link.request.TagRequest;
import com.io.linkapp.link.response.TagResponse.GetAll;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Quartz", tags = {"Quartz"})
@RequiredArgsConstructor
@RestController
public class TestApi {

    private final QuartzService service;
    
    
    @SneakyThrows
    @ApiOperation("5분 간격 테스트")
    @GetMapping("/quartz")
    public void testQuartz(){
        System.out.println("testApi");
        service.init();
    }
    
}
