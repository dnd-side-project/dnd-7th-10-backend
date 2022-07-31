package com.io.linkapp.link.controller.api;

import com.io.linkapp.link.request.MemoRequest;
import com.io.linkapp.link.response.MemoResponse;
import com.io.linkapp.link.service.MemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("/memo/{id}")
    public ResponseEntity<MemoResponse> get(@PathVariable("id") UUID uuid){
        MemoResponse memoResponse = memoService.findById(uuid);
        return ResponseEntity.ok().body(memoResponse);
    }

    @ApiOperation("메모 전체 조회")
    @GetMapping("/memos")
    public ResponseEntity<List<MemoResponse>> getList(){
        List<MemoResponse> memos = memoService.getList();
        return ResponseEntity.ok(memos);
    }

    @ApiOperation("메모 등록")
    @PostMapping("/memo")
    public void add(@RequestBody @Valid MemoRequest memoRequest) {
        memoService.add(memoRequest);
    }

    @ApiOperation("메모 수정")
    @PatchMapping("/memo/{id}")
    public void modify(@PathVariable("id") UUID uuid, @RequestBody @Valid MemoRequest memoRequest) {
        memoService.modify(uuid, memoRequest);
    }

    @ApiOperation("메모 삭제")
    @DeleteMapping("memo/{id}")
    public void remove(@PathVariable("id") UUID uuid) {
        memoService.remove(uuid);
    }
}
