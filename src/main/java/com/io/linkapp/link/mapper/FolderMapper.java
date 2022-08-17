package com.io.linkapp.link.mapper;

import com.io.linkapp.link.domain.Folder;
import com.io.linkapp.link.request.FolderRequest;
import com.io.linkapp.link.response.FolderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FolderMapper {
    FolderMapper INSTANCE = Mappers.getMapper(FolderMapper.class);
    Folder toEntity(FolderRequest folderRequest);
    FolderResponse toResponseDto(Folder folder);
    FolderResponse.GetAll toResponseAll(Folder folder);
    FolderResponse.GetArticles toFolderArticles(Folder folder);
}
