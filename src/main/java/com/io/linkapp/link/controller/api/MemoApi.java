package com.io.linkapp.link.controller.api;

import com.io.linkapp.config.security.auth.PrincipalDetails;
import com.io.linkapp.link.request.MemoRequest;
import com.io.linkapp.link.response.MemoResponse;
import com.io.linkapp.link.response.SuccessResponse;
import com.io.linkapp.link.service.MemoService;
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

@Api(value = "Memo", tags = {"Memo"})
@RequiredArgsConstructor
@RestController
public class MemoApi {

    private final MemoService memoService;

    @ApiOperation("메모 단건 조회")
    @GetMapping("/memo/{memoId}")
    public MemoResponse get(@PathVariable UUID memoId){
        return memoService.findById(memoId);
    }

    @ApiOperation("메모 전체 조회")
    @GetMapping("/memos")
    public List<MemoResponse> getList(@AuthenticationPrincipal PrincipalDetails principalDetails){
        return memoService.getList(principalDetails.getUser());
    }

    @ApiOperation("메모 등록")
    @PostMapping("/memo")
    public MemoResponse add(@RequestBody @Valid MemoRequest memoRequest, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return memoService.add(memoRequest, principalDetails.getUser());
    }

    @ApiOperation("메모 수정")
    @PatchMapping("/memo/{memoId}")
    public MemoResponse modify(@PathVariable UUID memoId, @RequestBody @Valid String memoContent) {
        return memoService.modify(memoId, memoContent);
    }

    @ApiOperation("메모 삭제")
    @DeleteMapping("memo/{memoId}")
    public SuccessResponse remove(@PathVariable UUID memoId) {
        return memoService.remove(memoId);
    }
}
