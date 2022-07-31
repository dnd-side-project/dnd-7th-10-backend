package com.io.linkapp.link.mapper;

import com.io.linkapp.link.domain.Article;
import com.io.linkapp.link.request.ArticleRequest;
import com.io.linkapp.link.response.ArticleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ArticleMapper {
    ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);

    Article toEntity(ArticleRequest articleRequest);
    ArticleResponse toResponseDto(Article article);
}
