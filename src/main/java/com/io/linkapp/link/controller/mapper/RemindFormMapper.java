package com.io.linkapp.link.controller.mapper;

import com.io.linkapp.link.domain.Remind;
import com.io.linkapp.link.request.RemindRequest;
import com.io.linkapp.link.response.RemindResponse;
import java.util.List;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(
    implementationName = "RemindFormMapperImpl",
    builder=@Builder(disableBuilder = true),
    componentModel = "spring"
)
public abstract class RemindFormMapper {
    
    public abstract Remind toRemind(RemindRequest.Add in);
    public abstract RemindResponse.GetAll toGetAll(Remind in);
    public abstract List<RemindResponse.GetAll> toGetAllList(List<Remind> in);

}
