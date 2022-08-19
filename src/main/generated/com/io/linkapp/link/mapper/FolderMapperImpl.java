package com.io.linkapp.link.mapper;

import com.io.linkapp.link.domain.Article;
import com.io.linkapp.link.domain.Folder;
import com.io.linkapp.link.domain.Folder.FolderBuilder;
import com.io.linkapp.link.domain.Memo;
import com.io.linkapp.link.request.FolderRequest;
import com.io.linkapp.link.response.ArticleResponse.Tags;
import com.io.linkapp.link.response.ArticleResponse.Tags.TagsBuilder;
import com.io.linkapp.link.response.FolderResponse;
import com.io.linkapp.link.response.FolderResponse.FolderResponseBuilder;
import com.io.linkapp.link.response.FolderResponse.GetAll;
import com.io.linkapp.link.response.FolderResponse.GetAll.GetAllBuilder;
import com.io.linkapp.link.response.FolderResponse.GetArticles;
import com.io.linkapp.link.response.FolderResponse.GetArticles.GetArticlesBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-19T21:19:47+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
public class FolderMapperImpl implements FolderMapper {

    @Override
    public Folder toEntity(FolderRequest folderRequest) {
        if ( folderRequest == null ) {
            return null;
        }

        FolderBuilder folder = Folder.builder();

        folder.folderTitle( folderRequest.getFolderTitle() );
        folder.folderColor( folderRequest.getFolderColor() );

        return folder.build();
    }

    @Override
    public FolderResponse toResponseDto(Folder folder) {
        if ( folder == null ) {
            return null;
        }

        FolderResponseBuilder folderResponse = FolderResponse.builder();

        folderResponse.folderId( folder.getFolderId() );
        folderResponse.folderTitle( folder.getFolderTitle() );
        folderResponse.folderColor( folder.getFolderColor() );

        return folderResponse.build();
    }

    @Override
    public GetAll toResponseAll(Folder folder) {
        if ( folder == null ) {
            return null;
        }

        GetAllBuilder getAll = GetAll.builder();

        getAll.folderId( folder.getFolderId() );
        getAll.folderTitle( folder.getFolderTitle() );
        getAll.folderColor( folder.getFolderColor() );

        return getAll.build();
    }

    @Override
    public GetArticles toFolderArticles(Folder folder) {
        if ( folder == null ) {
            return null;
        }

        GetArticlesBuilder getArticles = GetArticles.builder();

        getArticles.folderId( folder.getFolderId() );
        getArticles.folderTitle( folder.getFolderTitle() );
        getArticles.folderColor( folder.getFolderColor() );
        getArticles.articles( articleListToTagsList( folder.getArticles() ) );

        return getArticles.build();
    }

    protected Tags articleToTags(Article article) {
        if ( article == null ) {
            return null;
        }

        TagsBuilder tags = Tags.builder();

        tags.id( article.getId() );
        tags.remindId( article.getRemindId() );
        tags.linkUrl( article.getLinkUrl() );
        tags.openGraph( article.getOpenGraph() );
        List<Memo> list = article.getMemos();
        if ( list != null ) {
            tags.memos( new ArrayList<Memo>( list ) );
        }
        tags.registerDate( article.getRegisterDate() );
        tags.modifiedDate( article.getModifiedDate() );

        return tags.build();
    }

    protected List<Tags> articleListToTagsList(List<Article> list) {
        if ( list == null ) {
            return null;
        }

        List<Tags> list1 = new ArrayList<Tags>( list.size() );
        for ( Article article : list ) {
            list1.add( articleToTags( article ) );
        }

        return list1;
    }
}
