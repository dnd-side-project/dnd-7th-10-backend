package com.io.linkapp.link.controller;

import com.io.linkapp.link.domain.Memo;
import com.io.linkapp.link.service.MemoService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.io.linkapp.request.MemoRequest;
import com.io.linkapp.response.MemoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class MemoController {

    private final MemoService memoService;

    @GetMapping("/api/memo/{id}")
    public ResponseEntity<MemoResponse> findMemoById(@PathVariable("id") UUID uuid){
        MemoResponse memoResponse = memoService.findMemoById(uuid);
        return ResponseEntity.ok().body(memoResponse);
    }

    @GetMapping("/api/memos")
    public ResponseEntity<List<Memo>> findAll(){
        List<Memo> memos = memoService.findAllMemo();
        return ResponseEntity.ok(memos);
    }

    @PostMapping("/api/memo")
    public void memo(@RequestBody @Valid MemoRequest memoRequest) {
        memoService.save(memoRequest);
    }

    @PatchMapping("/api/memo/{id}")
    public void editMemo(@PathVariable("id") UUID uuid, @RequestBody @Valid MemoRequest memoRequest) {
        memoService.editMemo(uuid, memoRequest);
    }

    @DeleteMapping("/api/memo/{id}")
    public void deleteMemo(@PathVariable("id") UUID uuid) {
        memoService.deleteMemo(uuid);
    }
}
