package com.io.linkapp.link.service;

import com.io.linkapp.exception.CustomGlobalException;
import com.io.linkapp.exception.ErrorCode;
import com.io.linkapp.link.domain.Article;
import com.io.linkapp.link.domain.ArticleTag;
import com.io.linkapp.link.domain.Folder;
import com.io.linkapp.link.domain.Tag;
import com.io.linkapp.link.mapper.ArticleMapper;
import com.io.linkapp.link.repository.ArticleRepository;
import com.io.linkapp.link.repository.ArticleTagRepository;
import com.io.linkapp.link.repository.FolderRepository;
import com.io.linkapp.link.repository.TagRepository;
import com.io.linkapp.link.request.ArticleRequest;
import com.io.linkapp.link.response.ArticleResponse;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final FolderRepository folderRepository;
    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;
    private final ArticleTagRepository articleTagRepository;

    public ArticleResponse findById(UUID id) {
        Article article = articleRepository.findById(id)
            .orElseThrow(() -> new CustomGlobalException(ErrorCode.ARTICLE_NOT_FOUND));

        return ArticleMapper.INSTANCE.toResponseDto(article);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void add(ArticleRequest articleRequest){
        Folder folder = folderRepository.findById(articleRequest.getFolderId())
            .orElseThrow(() -> new CustomGlobalException(ErrorCode.FOLDER_NOT_FOUND));

        List<Tag> tags = articleRequest.getTagIds().stream()
            .map(tagId -> tagRepository.findById(tagId)
                .orElseThrow(() -> new CustomGlobalException(ErrorCode.TAG_NOT_FOUND)))
            .collect(Collectors.toList());

        Article article = Article.builder()
            .folder(folder)
            .linkContent(articleRequest.getLinkContent())
            .linkTitle(articleRequest.getLinkTitle())
            .build();

        articleRepository.save(article);

        for (Tag tag : tags) {
            ArticleTag articleTag = ArticleTag.builder()
                .tag(tag)
                .article(article)
                .build();

            articleTagRepository.save(articleTag);
        }
    }

    public List<ArticleResponse> getList(){
        return articleRepository.findAll().stream().map(
            article -> ArticleMapper.INSTANCE.toResponseDto(article)
        ).collect(Collectors.toList());
    }

    public void remove(UUID uuid){
        Article article = articleRepository.findById(uuid)
            .orElseThrow(() -> new CustomGlobalException(ErrorCode.ARTICLE_NOT_FOUND));

        articleRepository.delete(article);
    }

    public ArticleResponse bookmark(UUID uuid) {
        Article article = articleRepository.findById(uuid)
            .orElseThrow(() -> new CustomGlobalException(ErrorCode.ARTICLE_NOT_FOUND));

        if(article.isBookmark() == false) {
            article.setBookmark(true);
        }else {
            article.setBookmark(false);
        }

        return ArticleMapper.INSTANCE.toResponseDto(articleRepository.save(article));
    }
}
