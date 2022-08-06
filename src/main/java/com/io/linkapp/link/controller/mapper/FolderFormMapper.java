package com.io.linkapp.link.controller.mapper;

import com.io.linkapp.link.domain.Folder;
import com.io.linkapp.link.request.FolderRequest;
import com.io.linkapp.link.response.FolderResponse;
import com.io.linkapp.link.response.FolderResponse.GetAll;
import java.util.List;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(
    implementationName = "FolderFormMapperImpl",
    builder=@Builder(disableBuilder = true),
    componentModel = "spring"
)
public abstract class FolderFormMapper {
    
    public abstract Folder toFolder(FolderRequest.Add in);
    public abstract Folder toFolder(FolderRequest.Modify in);
    public abstract FolderResponse.GetAll toGetAll(Folder in);
    
    public abstract List<GetAll> toGetAllList(List<Folder> in);
    
}
