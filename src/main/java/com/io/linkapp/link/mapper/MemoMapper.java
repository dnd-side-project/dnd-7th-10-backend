package com.io.linkapp.link.mapper;

import com.io.linkapp.link.domain.Memo;
import com.io.linkapp.link.request.MemoRequest;
import com.io.linkapp.link.response.MemoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MemoMapper {
    MemoMapper INSTANCE = Mappers.getMapper(MemoMapper.class);

    Memo toEntity(MemoRequest memoRequest);
    MemoResponse toResponseDto(Memo memo);
}
