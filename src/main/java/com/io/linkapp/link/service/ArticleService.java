package com.io.linkapp.link.service;

import com.io.linkapp.common.OpenGraphParser;
import com.io.linkapp.exception.CustomGlobalException;
import com.io.linkapp.exception.ErrorCode;
import com.io.linkapp.link.domain.*;
import com.io.linkapp.link.mapper.ArticleMapper;
import com.io.linkapp.link.repository.*;
import com.io.linkapp.link.request.ArticleRequest;
import com.io.linkapp.link.request.ArticleTagRequest;
import com.io.linkapp.link.response.ArticleResponse;
import com.io.linkapp.link.response.ArticleResponse.Tags;
import com.io.linkapp.link.response.ArticleResponse.Tags.TagsBuilder;
import com.io.linkapp.link.response.ArticleTagResponse;
import com.io.linkapp.link.response.SuccessResponse;
import com.io.linkapp.user.domain.User;
import com.io.linkapp.user.repository.UserRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final FolderRepository folderRepository;
    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;
    private final RemindRepository remindRepository;
    private final ArticleTagRepository articleTagRepository;
    private final OpenGraphParser openGraphParser;
    private final UserRepository userRepository;

    public ArticleResponse.Tags findById(UUID id) {
        Article article = articleRepository.findByIdWithTag(id)
            .orElseThrow(() -> new CustomGlobalException(ErrorCode.ARTICLE_NOT_FOUND));

        TagsBuilder articleTagResponseBuilder = Tags.builder()
            .id(article.getId())
            .remindId(article.getRemindId())
            .linkUrl(article.getLinkUrl())
            .openGraph(article.getOpenGraph())
            .memos(article.getMemos())
            .registerDate(article.getRegisterDate())
            .modifiedDate(article.getModifiedDate())
            .isBookmark(article.isBookmark());

        List<ArticleTag> articleTags = article.getArticleTags();
        List<ArticleTagResponse> tagsResponse = new ArrayList<>();

        if (articleTags != null) {
            for (ArticleTag articleTag : articleTags) {
                ArticleTagResponse tags = ArticleTagResponse.builder()
                    .tagId(articleTag.getTag().getTagId())
                    .tagName(articleTag.getTag().getTagName())
                    .build();

                tagsResponse.add(tags);
            }
        }

        return articleTagResponseBuilder
            .tags(tagsResponse)
            .build();
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public ArticleResponse add(ArticleRequest articleRequest, User user) {
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

        if (articleRequest.getTagIds() != null) {
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

    public List<ArticleResponse.Tags> searchArticle(Predicate search) {
        List<Article> articles = (List<Article>) articleRepository.findAll(search,
            Sort.by(Sort.Direction.DESC, "modifiedDate"));
        return ArticleResponse.Tags.articleTagBuilder(articles);
    }

    public SuccessResponse remove(UUID uuid) {
        Article article = articleRepository.findById(uuid)
            .orElseThrow(() -> new CustomGlobalException(ErrorCode.ARTICLE_NOT_FOUND));

        articleRepository.delete(article);

        return SuccessResponse.builder()
            .status(200)
            .message("Article Remove Success.")
            .build();
    }

    public ArticleResponse bookmark(UUID uuid, UUID userId) {
        Article article = articleRepository.findById(uuid)
            .orElseThrow(() -> new CustomGlobalException(ErrorCode.ARTICLE_NOT_FOUND));

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new CustomGlobalException(ErrorCode.USER_NOT_FOUND));

        QRemind qRemind = QRemind.remind;
        BooleanBuilder builder = new BooleanBuilder();

        if (article.isBookmark() == false) { //북마크 설정된 애들은 몽땅 default 리마인드에 넣어줌
            article.setBookmark(true);
            builder.and(qRemind.userId.eq(userId));
            builder.and(qRemind.remindTitle.eq("default"));
            Remind remind = remindRepository.findOne(builder)
                .orElseThrow(() -> new CustomGlobalException(ErrorCode.DEFAULT_REMIND_NOT_FOUND));
            article.setRemindId(remind.getRemindId());
        } else {
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

    public Tags modify(ArticleRequest.Modify modifyRequest) {
        Article article = articleRepository.findById(modifyRequest.getArticleId())
            .orElseThrow(() -> new CustomGlobalException(ErrorCode.ARTICLE_NOT_FOUND));

        List<ArticleTag> existArticleTags = article.getArticleTags();

        for (ArticleTag existArticleTag : existArticleTags) {
            articleTagRepository.delete(existArticleTag);
        }

        List<UUID> tagIds = modifyRequest.getTagIds();
        List<ArticleTag> articleTags = new ArrayList<>();
        List<ArticleTagResponse> tagResponses = new ArrayList<>();

        for (UUID tagId : tagIds) {
            Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new CustomGlobalException(ErrorCode.TAG_NOT_FOUND));

            ArticleTag articleTag = ArticleTag.builder()
                .article(article)
                .tag(tag)
                .build();

            articleTagRepository.save(articleTag);

            ArticleTagResponse tagResponse = ArticleTagResponse.builder()
                .tagId(tag.getTagId())
                .tagName(tag.getTagName())
                .build();

            tagResponses.add(tagResponse);
        }

        Folder folder = folderRepository.findById(modifyRequest.getFolderId())
            .orElseThrow(() -> new CustomGlobalException(ErrorCode.FOLDER_NOT_FOUND));

        article.modifyArticle(folder, articleTags);
        article = articleRepository.save(article);
        return ArticleResponse.Tags.builder()
            .id(article.getId())
            .remindId(article.getRemindId())
            .linkUrl(article.getLinkUrl())
            .openGraph(article.getOpenGraph())
            .memos(article.getMemos())
            .registerDate(article.getRegisterDate())
            .modifiedDate(article.getModifiedDate())
            .tags(tagResponses)
            .build();
    }

    public SuccessResponse removeTag(ArticleRequest.DeleteTag deleteTagRequest) {
        UUID tagId = deleteTagRequest.getTagId();
        UUID articleId = deleteTagRequest.getArticleId();

        Article article = articleRepository.findById(articleId)
            .orElseThrow(() -> new CustomGlobalException(ErrorCode.ARTICLE_NOT_FOUND));

        List<ArticleTag> articleTags = article.getArticleTags();

        for (ArticleTag articleTag : articleTags) {
            if (articleTag.getArticle().getId().equals(articleId) && articleTag.getTag().getTagId()
                .equals(tagId)) {

                articleTag = articleTagRepository.findByArticleAndTag(articleId, tagId);
                articleTagRepository.delete(articleTag);
            }
        }

        return SuccessResponse.builder()
            .status(200)
            .message("Tag Remove Success.")
            .build();
    }
}
