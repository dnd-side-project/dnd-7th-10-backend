package com.io.linkapp.link.mapper;

import com.io.linkapp.link.domain.Folder;
import com.io.linkapp.link.domain.Folder.FolderBuilder;
import com.io.linkapp.link.request.FolderRequest;
import com.io.linkapp.link.response.FolderResponse;
import com.io.linkapp.link.response.FolderResponse.FolderResponseBuilder;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-13T18:38:19+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
public class FolderMapperImpl implements FolderMapper {

    @Override
    public Folder toEntity(FolderRequest folderRequest) {
        if ( folderRequest == null ) {
            return null;
        }

        FolderBuilder folder = Folder.builder();

        folder.user( folderRequest.getUser() );
        folder.folderTitle( folderRequest.getFolderTitle() );

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

        return folderResponse.build();
    }
}
