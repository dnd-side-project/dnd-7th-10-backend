package com.io.linkapp.link.controller.mapper;


import com.io.linkapp.link.domain.Tag;
import com.io.linkapp.link.request.TagRequest;
import com.io.linkapp.link.response.TagResponse;
import com.io.linkapp.link.response.TagResponse.GetAll;
import java.util.List;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(
    implementationName = "TagFormMapperImpl",
    builder=@Builder(disableBuilder = true),
    componentModel = "spring"
)
public abstract class TagFormMapper {

    public abstract Tag toTag(TagRequest.Add in);
    public abstract Tag toTag(TagRequest.Modify in);
    public abstract TagResponse.GetAll toGetAll(Tag in);
    
    public abstract List<GetAll> toGetAllList(List<Tag> in);
}
