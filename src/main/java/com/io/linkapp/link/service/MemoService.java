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

import com.io.linkapp.user.domain.User;
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

    public MemoResponse add(MemoRequest memoRequest, User user){
        Article article = articleRepository.findById(memoRequest.getArticleId())
            .orElseThrow(() -> new CustomGlobalException(ErrorCode.ARTICLE_NOT_FOUND));

        Memo memo = Memo.builder()
                .content(memoRequest.getContent())
                .article(article)
                .user(user)
                .build();

        return MemoMapper.INSTANCE.toResponseDto(memoRepository.save(memo));
    }

    public List<MemoResponse> getList(User user){
        return memoRepository.findByUser(user).stream().map(
            memo -> MemoMapper.INSTANCE.toResponseDto(memo))
            .collect(Collectors.toList());
    }

    public void remove(UUID id){
        Memo memo = memoRepository.findById(id)
                .orElseThrow(() -> new CustomGlobalException(ErrorCode.MEMO_NOT_FOUND));

        memoRepository.delete(memo);
    }

    public MemoResponse modify(UUID uuid, String memoContent) {
        Memo memo = memoRepository.findById(uuid)
                .orElseThrow(() -> new CustomGlobalException(ErrorCode.MEMO_NOT_FOUND));

        memo.modify(memoContent);
        return MemoMapper.INSTANCE.toResponseDto(memoRepository.save(memo));
    }
}
