package com.io.linkapp.link.controller.api;


import com.io.linkapp.link.controller.mapper.RemindFormMapper;
import com.io.linkapp.link.controller.predicate.RemindFormPredicate;
import com.io.linkapp.link.request.RemindRequest;
import com.io.linkapp.link.response.RemindResponse;
import com.io.linkapp.link.service.RemindService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Remind", tags = {"Remind"})
@RequestMapping(value = "/remind", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class RemindApi {

    private final RemindService service;
    
    private final RemindFormMapper formMapper;
    
    @SneakyThrows
    @ApiOperation("페이징 조회")
    @GetMapping
    public Page<RemindResponse.GetAll> getPage(RemindRequest.GetAll in,
        @PageableDefault(size = 20, direction = Sort.Direction.DESC) Pageable page ){
        
        return service.getPage(RemindFormPredicate.search(in),page).map(formMapper::toGetAll);
    }
    
    @SneakyThrows
    @ApiOperation("등록")
    @PostMapping
    public RemindResponse.GetAll add(@Valid @RequestBody RemindRequest.Add in){
        return formMapper.toGetAll(service.add(formMapper.toRemind(in)));
    }
    
    @SneakyThrows
    @ApiOperation("삭제")
    @DeleteMapping("/{id}")
    public void remove(@PathVariable UUID id) {
        service.remove(id);
    }
    
    
    
    
}
