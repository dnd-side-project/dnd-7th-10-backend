package com.io.linkapp.link.service;

import com.io.linkapp.exception.ArticleNotFoundException;
import com.io.linkapp.exception.FolderNotFoundException;
import com.io.linkapp.link.domain.Article;
import com.io.linkapp.link.domain.Folder;
import com.io.linkapp.link.mapper.ArticleMapper;
import com.io.linkapp.link.repository.ArticleRepository;
import com.io.linkapp.link.repository.FolderRepository;
import com.io.linkapp.link.request.ArticleRequest;
import com.io.linkapp.link.response.ArticleResponse;
import com.io.linkapp.user.domain.User;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final FolderRepository folderRepository;
    private final ArticleRepository articleRepository;

    public ArticleResponse findById(UUID id) {
        Article article = articleRepository.findById(id)
            .orElseThrow(ArticleNotFoundException::new);

        return ArticleMapper.INSTANCE.toResponseDto(article);
    }

    public void add(ArticleRequest articleRequest){
        Folder folder = folderRepository.findById(articleRequest.getFolderId())
            .orElseThrow(FolderNotFoundException::new);

        Article article = Article.builder()
            .folder(folder)
            .linkContent(articleRequest.getLinkContent())
            .linkTitle(articleRequest.getLinkTitle())
            .build();

        articleRepository.save(article);
    }

    public List<ArticleResponse> getList(){
        return articleRepository.findAll().stream().map(
            article -> ArticleMapper.INSTANCE.toResponseDto(article)
        ).collect(Collectors.toList());
    }

    public void remove(UUID uuid){
        Article article = articleRepository.findById(uuid)
            .orElseThrow(ArticleNotFoundException::new);

        articleRepository.delete(article);
    }
}
