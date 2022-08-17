package com.io.linkapp.link.controller.mapper;

import com.io.linkapp.link.domain.Tag;
import com.io.linkapp.link.request.TagRequest.Add;
import com.io.linkapp.link.request.TagRequest.Modify;
import com.io.linkapp.link.response.TagResponse.GetAll;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-17T20:34:35+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
@Component
public class TagFormMapperImpl extends TagFormMapper {

    @Override
    public Tag toTag(Add in) {
        if ( in == null ) {
            return null;
        }

        Tag tag = new Tag();

        tag.setTagName( in.getTagName() );

        return tag;
    }

    @Override
    public Tag toTag(Modify in) {
        if ( in == null ) {
            return null;
        }

        Tag tag = new Tag();

        tag.setTagName( in.getTagName() );

        return tag;
    }

    @Override
    public GetAll toGetAll(Tag in) {
        if ( in == null ) {
            return null;
        }

        GetAll getAll = new GetAll();

        getAll.setTagId( in.getTagId() );
        getAll.setTagName( in.getTagName() );

        return getAll;
    }

    @Override
    public List<GetAll> toGetAllList(List<Tag> in) {
        if ( in == null ) {
            return null;
        }

        List<GetAll> list = new ArrayList<GetAll>( in.size() );
        for ( Tag tag : in ) {
            list.add( toGetAll( tag ) );
        }

        return list;
    }
}
