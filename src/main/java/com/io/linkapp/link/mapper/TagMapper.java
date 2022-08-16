package com.io.linkapp.link.mapper;


import com.io.linkapp.link.domain.Tag;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(
    implementationName = "TagMapperImpl",
    builder=@Builder(disableBuilder = true),
    componentModel = "spring"
)
public abstract class TagMapper {

    public Tag modify(Tag in, Tag out){
        
        if(in == null){
            return null;
        }

        
        out.setArticleId(in.getArticleId());
        out.setTagName(in.getTagName());
        
        return out;
    }
    
}
