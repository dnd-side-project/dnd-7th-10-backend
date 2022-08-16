package com.io.linkapp.link.controller.api;

import com.io.linkapp.link.controller.mapper.RemindFormMapper;
import com.io.linkapp.link.controller.predicate.RemindFormPredicate;
import com.io.linkapp.link.domain.Child;
import com.io.linkapp.link.domain.Parent;
import com.io.linkapp.link.request.RemindRequest;
import com.io.linkapp.link.response.RemindResponse;
import com.io.linkapp.link.response.RemindResponse.GetAll;
import com.io.linkapp.link.service.ChildService;
import com.io.linkapp.link.service.ParentService;
import com.io.linkapp.link.service.RemindService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Child", tags = {"Child"})
@RequestMapping(value = "/child", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class ChildApi {
    
    private final ChildService service;
    
    @SneakyThrows
    @ApiOperation("페이징 조회")
    @GetMapping
    public List<Child> getPage( ){
        
        return service.getPage();
    }
    
    @SneakyThrows
    @ApiOperation("등록")
    @PostMapping
    public Child add(String name, String parentId){
    
        Child child;
        
        if(parentId == null){
            child = Child.builder().childName(name).build();
        }else{
            child = Child.builder().childName(name).parentId(
                UUID.fromString(parentId)).build();
        }
        
        
        return service.add(child);
    }
    
    @SneakyThrows
    @ApiOperation("북마크 등록")
    @PostMapping("/do")
    public Child doBookmark(String Id){
       
        return service.doBookmark(Id);
    }
    
    @SneakyThrows
    @ApiOperation("북마크 해제")
    @PostMapping("/undo")
    public Child undoBookmark(String Id){
    
        return service.undoBookmark(Id);
    }
    
    @SneakyThrows
    @ApiOperation("삭제")
    @DeleteMapping("/{id}")
    public void remove(@PathVariable UUID id) {
        service.remove(id);
    }
    
    
    
}

