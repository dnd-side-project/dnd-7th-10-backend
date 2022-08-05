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
    date = "2022-08-05T20:25:18+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
public class ArticleMapperImpl implements ArticleMapper {

    @Override
    public Article toEntity(ArticleRequest articleRequest) {
        if ( articleRequest == null ) {
            return null;
        }

        ArticleBuilder article = Article.builder();

        article.userId( articleRequest.getUserId() );
        article.folderId( articleRequest.getFolderId() );
        article.remindId( articleRequest.getRemindId() );
        article.linkTitle( articleRequest.getLinkTitle() );
        article.linkContent( articleRequest.getLinkContent() );

        return article.build();
    }

    @Override
    public ArticleResponse toResponseDto(Article article) {
        if ( article == null ) {
            return null;
        }

        ArticleResponseBuilder articleResponse = ArticleResponse.builder();

        articleResponse.id( article.getId() );
        articleResponse.userId( article.getUserId() );
        articleResponse.folderId( article.getFolderId() );
        articleResponse.remindId( article.getRemindId() );
        articleResponse.linkTitle( article.getLinkTitle() );
        articleResponse.linkContent( article.getLinkContent() );
        List<Memo> list = article.getMemos();
        if ( list != null ) {
            articleResponse.memos( new ArrayList<Memo>( list ) );
        }
        articleResponse.registerDate( article.getRegisterDate() );
        articleResponse.modifiedDate( article.getModifiedDate() );

        return articleResponse.build();
    }
}
