package com.io.linkapp.link.controller.api;

import com.io.linkapp.link.controller.mapper.TagFormMapper;
import com.io.linkapp.link.controller.predicate.TagFormPredicate;
import com.io.linkapp.link.service.TagService;
import com.io.linkapp.request.TagRequest;
import com.io.linkapp.response.TagResponse;
import com.io.linkapp.response.TagResponse.GetAll;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/tag", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class TagApi {

    private final TagFormMapper formMapper;
    
    private final TagService service;
    
    @SneakyThrows
    //@ApiOperation("목록 조회")
    @GetMapping("/get-list")
    public List<GetAll> getList(@Valid TagRequest.GetAll in){
        return formMapper.toGetAllList(service.getList(TagFormPredicate.search(in)));
    }
    
    @SneakyThrows
    //@ApiPageable
    //@ApiOperation("페이징 조회")
    @GetMapping("/get-page")
    public Page<GetAll> getPage(@Valid TagRequest.GetAll in,
        @PageableDefault(size = 20, sort = "regDt", direction = Sort.Direction.DESC) Pageable page){
        return service.getPage(TagFormPredicate.search(in),page).map(formMapper::toGetAll);
    }
    
    @SneakyThrows
    //@ApiOperation("조회")
    @GetMapping("/get/{id}")
    public TagResponse.GetAll get(@PathVariable UUID id){
        return formMapper.toGetAll(service.get(id));
    }
    
    
    @SneakyThrows
    //@ApiOperation("등록")
    @PostMapping("/add")
    public TagResponse.GetAll add(@Valid @RequestBody TagRequest.Add in){
        return formMapper.toGetAll(service.add(formMapper.toTag(in)));
    }
    
    
    @SneakyThrows
    //@ApiOperation("수정")
    @PostMapping("/modify/{id}")
    public TagResponse.GetAll add(@PathVariable UUID id,@Valid @RequestBody TagRequest.Modify in){
        return formMapper.toGetAll(service.modify(id, formMapper.toTag(in)));
    }
    
    @SneakyThrows
    //@ApiOperation("삭제")
    @PostMapping("/remove/{id}")
    public void remove(@PathVariable UUID id) {
        service.remove(id);
    }
    
    
    
}
