package com.io.linkapp.link.controller;

import com.io.linkapp.link.domain.Memo;
import com.io.linkapp.link.service.MemoService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemoController {

    private final MemoService memoService;

    @GetMapping("/api/save")
    public void memo(){
        Memo memo = Memo.builder()
            .content("메모")
            .articleId(UUID.randomUUID())
            .build();

        memoService.save(memo);
    }

    @GetMapping("/api/memo")
    public ResponseEntity<Memo> findMemo(){
        UUID id = UUID.randomUUID();
        Memo body = memoService.findMemoById(id);

        return ResponseEntity.ok().body(body);
    }
}
