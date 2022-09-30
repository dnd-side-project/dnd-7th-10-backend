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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.io.linkapp.link.response.SuccessResponse;
import com.io.linkapp.user.domain.User;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemoService {

    private final MemoRepository memoRepository;
    private final ArticleRepository articleRepository;

    public MemoResponse findById(UUID id){
        Memo memo = memoRepository.findById(id)
                .orElseThrow(() -> new CustomGlobalException(ErrorCode.MEMO_NOT_FOUND));

        Article article = memo.getArticle();

        return MemoResponse.builder()
                .id(memo.getId())
                .content(memo.getContent())
                .articleId(memo.getArticle().getId())
                .modifiedDate(memo.getModifiedDate())
                .registerDate(memo.getRegisterDate())
                .openGraph(article.getOpenGraph())
                .folderTitle(article.getFolder().getFolderTitle())
                .build();
    }

    public MemoResponse add(MemoRequest memoRequest, User user){
        Article article = articleRepository.findById(memoRequest.getArticleId())
                .orElseThrow(() -> new CustomGlobalException(ErrorCode.ARTICLE_NOT_FOUND));

        Memo memo = Memo.builder()
                .content(memoRequest.getContent())
                .article(article)
                .user(user)
                .build();

        memo = memoRepository.save(memo);

        return MemoResponse.builder()
                .id(memo.getId())
                .content(memo.getContent())
                .articleId(memo.getArticle().getId())
                .folderTitle(memo.getArticle().getFolder().getFolderTitle())
                .openGraph(memo.getArticle().getOpenGraph())
                .modifiedDate(memo.getModifiedDate())
                .registerDate(memo.getRegisterDate())
                .build();
    }

    public List<MemoResponse> searchMemo(Predicate search) {
        List<Memo> memos = (List<Memo>) memoRepository.findAll(search, Sort.by(Direction.DESC, "registerDate"));
        return getMemoResponses(memos);
    }

    private List<MemoResponse> getMemoResponses(List<Memo> memos) {
        List<MemoResponse> memoResponses = new ArrayList<>();

        for (Memo memo : memos) {
            Article article = memo.getArticle();
            MemoResponse memoResponse = MemoResponse.builder()
                    .id(memo.getId())
                    .content(memo.getContent())
                    .articleId(memo.getArticle().getId())
                    .modifiedDate(memo.getModifiedDate())
                    .registerDate(memo.getRegisterDate())
                    .openGraph(article.getOpenGraph())
                    .folderTitle(article.getFolder().getFolderTitle())
                    .build();

            memoResponses.add(memoResponse);
        }

        return memoResponses;
    }

    public SuccessResponse remove(UUID id){
        Memo memo = memoRepository.findById(id)
                .orElseThrow(() -> new CustomGlobalException(ErrorCode.MEMO_NOT_FOUND));

        memoRepository.delete(memo);

        return SuccessResponse.builder()
                .status(200)
                .message("Memo Remove Success.")
                .build();
    }

    public MemoResponse modify(UUID uuid, String memoContent) {
        Memo memo = memoRepository.findById(uuid)
                .orElseThrow(() -> new CustomGlobalException(ErrorCode.MEMO_NOT_FOUND));

        memo.modify(memoContent);
        return MemoMapper.INSTANCE.toResponseDto(memoRepository.save(memo));
    }
}
