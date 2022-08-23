package com.io.linkapp.link.mapper;

import com.io.linkapp.link.domain.Article;
import com.io.linkapp.link.domain.Article.ArticleBuilder;
import com.io.linkapp.link.domain.Memo;
import com.io.linkapp.link.request.ArticleRequest;
import com.io.linkapp.link.response.ArticleResponse;
import com.io.linkapp.link.response.ArticleResponse.ArticleResponseBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-23T19:09:40+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
public class ArticleMapperImpl implements ArticleMapper {

    @Override
    public Article toEntity(ArticleRequest articleRequest) {
        if ( articleRequest == null ) {
            return null;
        }

        ArticleBuilder article = Article.builder();

        article.linkUrl( articleRequest.getLinkUrl() );

        return article.build();
    }

    @Override
    public ArticleResponse toResponseDto(Article article) {
        if ( article == null ) {
            return null;
        }

        ArticleResponseBuilder articleResponse = ArticleResponse.builder();

        articleResponse.id( article.getId() );
        articleResponse.remindId( article.getRemindId() );
        articleResponse.linkUrl( article.getLinkUrl() );
        articleResponse.openGraph( article.getOpenGraph() );
        List<Memo> list = article.getMemos();
        if ( list != null ) {
            articleResponse.memos( new ArrayList<Memo>( list ) );
        }
        articleResponse.registerDate( article.getRegisterDate() );
        articleResponse.modifiedDate( article.getModifiedDate() );

        return articleResponse.build();
    }
}
