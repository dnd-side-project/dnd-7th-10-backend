package com.io.linkapp.link.controller.mapper;

import com.io.linkapp.link.domain.Folder;
import com.io.linkapp.link.request.FolderRequest.Add;
import com.io.linkapp.link.request.FolderRequest.Modify;
import com.io.linkapp.link.response.FolderResponse.GetAll;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-05T20:25:18+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
@Component
public class FolderFormMapperImpl extends FolderFormMapper {

    @Override
    public Folder toFolder(Add in) {
        if ( in == null ) {
            return null;
        }

        Folder folder = new Folder();

        folder.setFolderTitle( in.getFolderTitle() );

        return folder;
    }

    @Override
    public Folder toFolder(Modify in) {
        if ( in == null ) {
            return null;
        }

        Folder folder = new Folder();

        folder.setFolderTitle( in.getFolderTitle() );

        return folder;
    }

    @Override
    public GetAll toGetAll(Folder in) {
        if ( in == null ) {
            return null;
        }

        GetAll getAll = new GetAll();

        getAll.setFolderId( in.getFolderId() );
        getAll.setFolderTitle( in.getFolderTitle() );

        return getAll;
    }

    @Override
    public List<GetAll> toGetAllList(List<Folder> in) {
        if ( in == null ) {
            return null;
        }

        List<GetAll> list = new ArrayList<GetAll>( in.size() );
        for ( Folder folder : in ) {
            list.add( toGetAll( folder ) );
        }

        return list;
    }
}
