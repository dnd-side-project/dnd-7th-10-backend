package com.io.linkapp.link.controller.api;

import com.io.linkapp.link.controller.mapper.FolderFormMapper;
import com.io.linkapp.link.controller.predicate.FolderFormPredicate;
import com.io.linkapp.link.request.FolderRequest;
import com.io.linkapp.link.response.FolderResponse;
import com.io.linkapp.link.response.FolderResponse.GetAll;
import com.io.linkapp.link.service.FolderService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Folder", tags = {"Folder"})
@RequestMapping(value = "/folder", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class FolderApi {
    
    private final FolderFormMapper formMapper;
    
    private final FolderService service;
    
//    @SneakyThrows
//    @ApiOperation("목록 조회")
//    @GetMapping("/get-list")
//    public List<GetAll> getList(@Valid FolderRequest.GetAll in){
//        return formMapper.toGetAllList(service.getList(FolderFormPredicate.search(in)));
//    }
    
    
    @SneakyThrows
    @ApiOperation("페이징 조회")
    @GetMapping
    public Page<GetAll> getPage(@Valid FolderRequest.GetAll in,
        @PageableDefault(size = 20, direction = Sort.Direction.DESC) Pageable page){
        return service.getPage(FolderFormPredicate.search(in),page).map(formMapper::toGetAll);
    }
    
    @SneakyThrows
    @ApiOperation("조회")
    @GetMapping("/{id}")
    public FolderResponse.GetAll get(@PathVariable UUID id){
        return formMapper.toGetAll(service.get(id));
    }
    
    @SneakyThrows
    @ApiOperation("등록")
    @PostMapping("/add")
    public FolderResponse.GetAll add(@Valid @RequestBody FolderRequest.Add in){
        System.out.println("Add in: "+ in.toString());
        return formMapper.toGetAll(service.add(formMapper.toFolder(in)));
    }
    
    @SneakyThrows
    @ApiOperation("수정")
    @PostMapping("/{id}")
    public FolderResponse.GetAll modify(@PathVariable UUID id,@Valid @RequestBody FolderRequest.Modify in){
        return formMapper.toGetAll(service.modify(id, formMapper.toFolder(in)));
    }
    
    @SneakyThrows
    @ApiOperation("삭제")
    @PostMapping("/remove/{id}")
    public void remove(@PathVariable UUID id) {
        service.remove(id);
    }
    
    
}
