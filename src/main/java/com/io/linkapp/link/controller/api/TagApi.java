package com.io.linkapp.link.controller.api;

import com.io.linkapp.link.controller.mapper.TagFormMapper;
import com.io.linkapp.link.controller.predicate.TagFormPredicate;
import com.io.linkapp.link.domain.Tag;
import com.io.linkapp.link.response.SuccessResponse;
import com.io.linkapp.link.service.TagService;
import com.io.linkapp.link.request.TagRequest;
import com.io.linkapp.link.response.TagResponse;
import com.io.linkapp.link.response.TagResponse.GetAll;
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

@Api(value = "Tag", tags = {"Tag"})
@RequestMapping(value = "/tag", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class TagApi {

    private final TagFormMapper formMapper;
    
    private final TagService service;
    
    @SneakyThrows
    @ApiOperation("전체 목록 조회")
    @GetMapping
    public List<GetAll> getList(@Valid TagRequest.GetAll in){
        return formMapper.toGetAllList(service.getList(TagFormPredicate.search(in)));
    }
    
    @SneakyThrows
    @ApiOperation("조회")
    @GetMapping("/{tagId}")
    public TagResponse.GetAll get(@PathVariable UUID tagId){
        return formMapper.toGetAll(service.get(tagId));
    }
    
    
    @SneakyThrows
    @ApiOperation("등록")
    @PostMapping
    public TagResponse.GetAll add(@Valid @RequestBody TagRequest.Add in){
        Tag newTag = formMapper.toTag(in);
        UUID articleId = in.getArticleId();
        return formMapper.toGetAll(service.add(newTag,articleId));
    }
    
    
//    @SneakyThrows
//    @ApiOperation("수정")
//    @PostMapping("/{tagId}")
//    public TagResponse.GetAll modify(@PathVariable UUID tagId,@Valid @RequestBody TagRequest.Modify in){
//        UUID articleId = in.getArticleId();
//        return formMapper.toGetAll(service.modify(tagId, formMapper.toTag(in),articleId));
//    }
    
    @SneakyThrows
    @ApiOperation("삭제")
    @DeleteMapping("/{tagId}")
    public SuccessResponse remove(@PathVariable UUID tagId) {
        return service.remove(tagId);
    }
}
