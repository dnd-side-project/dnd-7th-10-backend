package com.io.linkapp.link.service;

import com.io.linkapp.exception.MemoNotFoundException;
import com.io.linkapp.link.domain.Memo;
import com.io.linkapp.link.mapper.MemoMapper;
import com.io.linkapp.link.repository.MemoRepository;
import java.util.List;
import java.util.UUID;

import com.io.linkapp.link.request.MemoRequest;
import com.io.linkapp.link.response.MemoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemoService {

    private final MemoRepository memoRepository;

    public MemoResponse findById(UUID id){
        Memo memo = memoRepository.findById(id)
                .orElseThrow(MemoNotFoundException::new);

        return MemoMapper.INSTANCE.toResponseDto(memo);
    }

    public void add(MemoRequest memoRequest){
        memoRepository.save(MemoMapper.INSTANCE.toEntity(memoRequest));
    }

    public List<Memo> getList(){
        return memoRepository.findAll();
    }

    public void remove(UUID id){
        Memo memo = memoRepository.findById(id)
                .orElseThrow(MemoNotFoundException::new);

        memoRepository.delete(memo);
    }

    public void modify(UUID uuid, MemoRequest memoRequest) {
        Memo memo = memoRepository.findById(uuid)
                .orElseThrow(MemoNotFoundException::new);

        memo.modify(memoRequest.getContent());
        memoRepository.save(memo);
    }
}
