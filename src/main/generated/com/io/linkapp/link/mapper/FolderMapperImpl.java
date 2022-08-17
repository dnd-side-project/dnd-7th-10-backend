package com.io.linkapp.link.mapper;

import com.io.linkapp.link.domain.Article;
import com.io.linkapp.link.domain.Folder;
import com.io.linkapp.link.domain.Folder.FolderBuilder;
import com.io.linkapp.link.request.FolderRequest;
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
    date = "2022-08-18T03:11:20+0900",
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
        List<Article> list = folder.getArticles();
        if ( list != null ) {
            getArticles.articles( new ArrayList<Article>( list ) );
        }

        return getArticles.build();
    }
}
