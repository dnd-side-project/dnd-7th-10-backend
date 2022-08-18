package com.io.linkapp.link.service;

import com.io.linkapp.common.OpenGraphParser;
import com.io.linkapp.exception.CustomGlobalException;
import com.io.linkapp.exception.ErrorCode;
import com.io.linkapp.link.domain.Article;
import com.io.linkapp.link.domain.ArticleTag;
import com.io.linkapp.link.domain.Folder;
import com.io.linkapp.link.domain.OpenGraph;
import com.io.linkapp.link.domain.Tag;
import com.io.linkapp.link.mapper.ArticleMapper;
import com.io.linkapp.link.repository.ArticleRepository;
import com.io.linkapp.link.repository.ArticleTagRepository;
import com.io.linkapp.link.repository.FolderRepository;
import com.io.linkapp.link.repository.RemindRepository;
import com.io.linkapp.link.repository.TagRepository;
import com.io.linkapp.link.request.ArticleRequest;
import com.io.linkapp.link.request.ArticleTagRequest;
import com.io.linkapp.link.response.ArticleResponse;
import com.io.linkapp.link.response.ArticleResponse.ArticleResponseBuilder;
import com.io.linkapp.link.response.ArticleTagResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.io.linkapp.link.response.SuccessResponse;
import com.io.linkapp.user.domain.User;
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
    private final OpenGraphParser openGraphParser;

    public ArticleResponse findById(UUID id) {
        Article article = articleRepository.findByIdWithTag(id)
            .orElseThrow(() -> new CustomGlobalException(ErrorCode.ARTICLE_NOT_FOUND));

        ArticleResponseBuilder articleResponseBuilder = ArticleResponse.builder()
            .id(article.getId())
            .remindId(article.getRemindId())
            .linkUrl(article.getLinkUrl())
            .openGraph(article.getOpenGraph())
            .memos(article.getMemos())
            .registerDate(article.getRegisterDate())
            .modifiedDate(article.getModifiedDate());

        List<ArticleTag> articleTags = article.getArticleTags();

        List<ArticleTagResponse> tagsResponse = new ArrayList<>();

        for (ArticleTag articleTag : articleTags) {
            ArticleTagResponse tags = ArticleTagResponse.builder()
                .tagId(articleTag.getTag().getTagId())
                .tagName(articleTag.getTag().getTagName())
                .build();

            tagsResponse.add(tags);
        }

        return articleResponseBuilder
            .tags(tagsResponse)
            .build();
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public ArticleResponse add(ArticleRequest articleRequest, User user){
        Folder folder = folderRepository.findById(articleRequest.getFolderId())
            .orElseThrow(() -> new CustomGlobalException(ErrorCode.FOLDER_NOT_FOUND));

        OpenGraph openGraph = openGraphParser.parse(articleRequest.getLinkUrl());

        Article article = Article.builder()
            .user(user)
            .folder(folder)
            .linkUrl(articleRequest.getLinkUrl())
            .openGraph(openGraph)
            .build();

        article = articleRepository.save(article);

        if(articleRequest.getTagIds() != null) {
            List<Tag> tags = articleRequest.getTagIds().stream()
                .map(tagId -> tagRepository.findById(tagId)
                    .orElseThrow(() -> new CustomGlobalException(ErrorCode.TAG_NOT_FOUND)))
                .collect(Collectors.toList());

            for (Tag tag : tags) {
                ArticleTag articleTag = ArticleTag.builder()
                    .tag(tag)
                    .article(article)
                    .build();

                articleTagRepository.save(articleTag);
            }
        }

        return ArticleMapper.INSTANCE.toResponseDto(article);
    }

    public List<ArticleResponse> getList(User user){
        return articleRepository.findByUser(user).stream().map(
            article -> ArticleMapper.INSTANCE.toResponseDto(article)
        ).collect(Collectors.toList());
    }

    public SuccessResponse remove(UUID uuid){
        Article article = articleRepository.findById(uuid)
            .orElseThrow(() -> new CustomGlobalException(ErrorCode.ARTICLE_NOT_FOUND));

        articleRepository.delete(article);

        return SuccessResponse.builder()
                .status(200)
                .message("Article Remove Success.")
                .build();
    }

    public ArticleResponse bookmark(UUID uuid) {
        Article article = articleRepository.findById(uuid)
            .orElseThrow(() -> new CustomGlobalException(ErrorCode.ARTICLE_NOT_FOUND));

        if(article.isBookmark() == false) {
            article.setBookmark(true);
            article.setRemindId(UUID.fromString( "00000000-0000-0000-0000-000000000000"));
        }else {
            article.setBookmark(false);
            article.setRemindId(null);
        }

        ArticleResponse articleResponse = ArticleMapper.INSTANCE.toResponseDto(
            articleRepository.save(article));
        articleResponse.setBookmark(article.isBookmark());

        return articleResponse;
    }

    public SuccessResponse setTagInArticle(ArticleTagRequest articleTagRequest) {
        Tag tag = tagRepository.findById(articleTagRequest.getTagId())
            .orElseThrow(() -> new CustomGlobalException(ErrorCode.TAG_NOT_FOUND));

        Article article = articleRepository.findById(articleTagRequest.getArticleId())
            .orElseThrow(() -> new CustomGlobalException(ErrorCode.ARTICLE_NOT_FOUND));

        ArticleTag articleTag = ArticleTag.builder()
            .article(article)
            .tag(tag)
            .build();

        articleTagRepository.save(articleTag);

        return SuccessResponse.builder()
            .status(200)
            .message("Set Tag Success")
            .build();
    }
}
