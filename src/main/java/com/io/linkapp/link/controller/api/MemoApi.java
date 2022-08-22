package com.io.linkapp.link.controller.api;

import com.io.linkapp.config.security.auth.PrincipalDetails;
import com.io.linkapp.link.controller.predicate.MemoFormPredicate;
import com.io.linkapp.link.request.MemoRequest;
import com.io.linkapp.link.response.MemoResponse;
import com.io.linkapp.link.response.SuccessResponse;
import com.io.linkapp.link.service.MemoService;
import com.io.linkapp.user.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

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

    @ApiOperation(value = "메모 전체 조회 및 검색", notes = "조회 시 파라미터 없이, 검색 시 쿼리 스트링으로 요청")
    @GetMapping("/memos")
    public List<MemoResponse> searchByContent(String content, @AuthenticationPrincipal PrincipalDetails principalDetails){
        User user = principalDetails.getUser();
        MemoRequest.Search searchInput = MemoRequest.Search.builder()
                .content(content)
                .user(user)
                .build();
        return memoService.searchMemo(MemoFormPredicate.search(searchInput));
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
