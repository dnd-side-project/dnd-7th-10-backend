package com.io.linkapp.link.controller.api;

import com.io.linkapp.config.security.auth.PrincipalDetails;
import com.io.linkapp.link.request.FolderRequest;
import com.io.linkapp.link.response.FolderResponse;
import com.io.linkapp.link.service.FolderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Folder", tags = {"Folder"})
@RestController
@RequiredArgsConstructor
public class FolderApi {

    private final FolderService folderService;

    @ApiOperation("폴더 조회")
    @GetMapping("/folder/{folderId}")
    public FolderResponse.GetArticles get(@PathVariable UUID id) {
        return folderService.get(id);
    }

    //TODO : 폴더 내부의 아티클 카운트로 정렬되도록 수정?
    @ApiOperation("유저가 작성한 폴더 전체 조회")
    @GetMapping("/folders")
    public List<FolderResponse.GetAll> getAll(@AuthenticationPrincipal PrincipalDetails principalDetails){
        return folderService.getFoldersByUser(principalDetails.getUser());
    }

    @ApiOperation("폴더 등록")
    @PostMapping("/folder")
    public FolderResponse add(@Valid @RequestBody FolderRequest folderRequest, @AuthenticationPrincipal PrincipalDetails principalDetails){
        return folderService.add(folderRequest, principalDetails.getUser());
    }

    @ApiOperation("폴더 이름 수정")
    @PatchMapping("/folder/{folderId}")
    public FolderResponse modify(@PathVariable("folderId") UUID uuid,@Valid @RequestBody FolderRequest folderRequest){
        return folderService.edit(uuid, folderRequest);
    }

    @ApiOperation("폴더 삭제")
    @DeleteMapping("/folder/{folderId}")
    public void remove(@PathVariable("folderId") UUID id) {
        folderService.remove(id);
    }
}
