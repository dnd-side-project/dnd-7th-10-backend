package com.io.linkapp.user.mapper;

import com.io.linkapp.user.domain.User;
import com.io.linkapp.user.domain.User.UserBuilder;
import com.io.linkapp.user.request.Oauth2UserRequest;
import com.io.linkapp.user.request.UserRequest;
import com.io.linkapp.user.response.UserResponse;
import com.io.linkapp.user.response.UserResponse.UserResponseBuilder;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-18T03:11:19+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserRequest userRequest) {
        if ( userRequest == null ) {
            return null;
        }

        UserBuilder user = User.builder();

        user.username( userRequest.getUsername() );
        user.password( userRequest.getPassword() );

        return user.build();
    }

    @Override
    public User toEntity(Oauth2UserRequest userRequest) {
        if ( userRequest == null ) {
            return null;
        }

        UserBuilder user = User.builder();

        user.username( userRequest.getUsername() );

        return user.build();
    }

    @Override
    public UserResponse toResponseDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.id( user.getId() );
        userResponse.username( user.getUsername() );
        userResponse.role( user.getRole() );
        userResponse.remind( user.getRemind() );

        return userResponse.build();
    }
}
