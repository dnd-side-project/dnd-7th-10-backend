package com.io.linkapp.link.mapper;

import com.io.linkapp.link.domain.Memo;
import com.io.linkapp.link.domain.Memo.MemoBuilder;
import com.io.linkapp.link.request.MemoRequest;
import com.io.linkapp.link.response.MemoResponse;
import com.io.linkapp.link.response.MemoResponse.MemoResponseBuilder;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-19T21:19:47+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
public class MemoMapperImpl implements MemoMapper {

    @Override
    public Memo toEntity(MemoRequest memoRequest) {
        if ( memoRequest == null ) {
            return null;
        }

        MemoBuilder memo = Memo.builder();

        memo.content( memoRequest.getContent() );

        return memo.build();
    }

    @Override
    public MemoResponse toResponseDto(Memo memo) {
        if ( memo == null ) {
            return null;
        }

        MemoResponseBuilder memoResponse = MemoResponse.builder();

        memoResponse.id( memo.getId() );
        memoResponse.content( memo.getContent() );
        memoResponse.registerDate( memo.getRegisterDate() );
        memoResponse.modifiedDate( memo.getModifiedDate() );

        return memoResponse.build();
    }
}
