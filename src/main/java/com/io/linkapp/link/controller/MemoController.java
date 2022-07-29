package com.io.linkapp.link.controller;

import com.io.linkapp.link.domain.Memo;
import com.io.linkapp.link.service.MemoService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class MemoController {

    private final MemoService memoService;

    @GetMapping("/api/memo/{id}")
    public ResponseEntity<Memo> findMemoById(@PathVariable("id") UUID uuid){
        Memo memo = memoService.findMemoById(uuid);
        return ResponseEntity.ok().body(memo);
    }

    @GetMapping("/api/memos")
    public ResponseEntity<List<Memo>> findAll(){
        List<Memo> memos = memoService.findAllMemo();
        return ResponseEntity.ok(memos);
    }

    @PostMapping("/api/memo")
    public void memo(@RequestBody @Valid Memo memo) {
        memoService.save(memo);
    }
}
