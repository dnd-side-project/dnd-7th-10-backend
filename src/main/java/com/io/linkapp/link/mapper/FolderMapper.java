package com.io.linkapp.link.mapper;

import com.io.linkapp.link.domain.Folder;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(
    implementationName = "FolderMapperImpl",
    builder=@Builder(disableBuilder = true),
    componentModel = "spring"
)
public abstract class FolderMapper {
    
    public Folder modify(Folder in, Folder out){
        
        if(in == null){
            return null;
        }
        
        out.setFolderId(in.getFolderId());
        out.setFolderTitle(in.getFolderTitle());
        
        return out;
    }
    
}
