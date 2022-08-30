package com.io.linkapp.user.response;

import com.io.linkapp.user.domain.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class UserResponse {
    private UUID id;
    private String username;
    private Role role;
}
