package com.io.linkapp.link.service;

import com.io.linkapp.exception.CustomGlobalException;
import com.io.linkapp.exception.ErrorCode;
import com.io.linkapp.link.domain.Article;
import com.io.linkapp.link.domain.Memo;
import com.io.linkapp.link.mapper.MemoMapper;
import com.io.linkapp.link.repository.ArticleRepository;
import com.io.linkapp.link.repository.MemoRepository;
import com.io.linkapp.link.request.MemoRequest;
import com.io.linkapp.link.response.MemoResponse;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemoService {

    private final MemoRepository memoRepository;
    private final ArticleRepository articleRepository;

    public MemoResponse findById(UUID id){
        Memo memo = memoRepository.findById(id)
                .orElseThrow(() -> new CustomGlobalException(ErrorCode.MEMO_NOT_FOUND));

        return MemoMapper.INSTANCE.toResponseDto(memo);
    }

    public void add(MemoRequest memoRequest){
        Article article = articleRepository.findById(memoRequest.getArticleId())
            .orElseThrow(() -> new CustomGlobalException(ErrorCode.ARTICLE_NOT_FOUND));

        Memo memo = MemoMapper.INSTANCE.toEntity(memoRequest);
        memo.addMemoToArticle(article);

        memoRepository.save(memo);
    }

    public List<MemoResponse> getList(){
        return memoRepository.findAll().stream().map(
            memo -> MemoMapper.INSTANCE.toResponseDto(memo))
            .collect(Collectors.toList());
    }

    public void remove(UUID id){
        Memo memo = memoRepository.findById(id)
                .orElseThrow(() -> new CustomGlobalException(ErrorCode.MEMO_NOT_FOUND));

        memoRepository.delete(memo);
    }

    public void modify(UUID uuid, MemoRequest memoRequest) {
        Memo memo = memoRepository.findById(uuid)
                .orElseThrow(() -> new CustomGlobalException(ErrorCode.MEMO_NOT_FOUND));

        memo.modify(memoRequest.getContent());
        memoRepository.save(memo);
    }
}
