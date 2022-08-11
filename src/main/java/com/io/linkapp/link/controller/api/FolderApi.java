package com.io.linkapp.link.controller.api;

import com.io.linkapp.link.controller.predicate.FolderFormPredicate;
import com.io.linkapp.link.mapper.FolderMapper;
import com.io.linkapp.link.request.FolderRequest;
import com.io.linkapp.link.response.FolderResponse;
import com.io.linkapp.link.service.FolderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Api(value = "Folder", tags = {"Folder"})
@RestController
@RequiredArgsConstructor
public class FolderApi {

    private final FolderService folderService;

    @ApiOperation("페이징 조회")
    @GetMapping
    public Page<FolderResponse> getPage(@Valid FolderRequest folderRequest,
                                        @PageableDefault(size = 20, direction = Sort.Direction.DESC) Pageable page){
        return folderService.getPage(FolderFormPredicate.search(folderRequest),page).map(FolderMapper.INSTANCE::toResponseDto);
    }

    @ApiOperation("조회")
    @GetMapping("/folder/{id}")
    public FolderResponse get(@PathVariable UUID id) {
        return FolderMapper.INSTANCE.toResponseDto(folderService.get(id));
    }

    @ApiOperation("등록")
    @PostMapping("/folder")
    public FolderResponse add(@Valid @RequestBody FolderRequest folderRequest){
        return folderService.add(folderRequest);
    }

    @ApiOperation("수정")
    @PatchMapping("/folder/{id}")
    public void modify(@PathVariable UUID id,@Valid @RequestBody FolderRequest folderRequest){
    }

    @ApiOperation("삭제")
    @DeleteMapping("/folder/{id}")
    public void remove(@PathVariable UUID id) {
        folderService.remove(id);
    }

}
