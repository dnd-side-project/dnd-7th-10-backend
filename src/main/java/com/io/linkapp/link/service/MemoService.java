package com.io.linkapp.link.service;

import com.io.linkapp.exception.MemoNotFoundException;
import com.io.linkapp.link.domain.Memo;
import com.io.linkapp.link.repository.MemoRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemoService {

    private final MemoRepository memoRepository;

    public Memo findMemoById(UUID id){
        return memoRepository.findById(id).orElseThrow(MemoNotFoundException::new);
    }

    public void save(Memo memo){
        memoRepository.save(memo);
    }

    public List<Memo> findAllMemo(){
        return memoRepository.findAll();
    }
}
