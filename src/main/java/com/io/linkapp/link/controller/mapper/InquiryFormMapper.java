package com.io.linkapp.link.controller.mapper;

import com.io.linkapp.link.domain.Inquiry;
import com.io.linkapp.link.request.InquiryRequest;
import com.io.linkapp.link.response.InquiryResponse;
import com.io.linkapp.link.response.InquiryResponse.GetAll;
import java.util.List;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(
    implementationName = "InquiryFormMapperImpl",
    builder=@Builder(disableBuilder = true),
    componentModel = "spring"
)
public abstract class InquiryFormMapper {
    public abstract Inquiry toInquiry(InquiryRequest.Add in);
    public abstract Inquiry toInquiry(InquiryRequest.Modify in);
    
    public abstract InquiryResponse.GetAll toGetAll(Inquiry in);
    public abstract List<GetAll> toGetAllList(List<Inquiry> in);

}
