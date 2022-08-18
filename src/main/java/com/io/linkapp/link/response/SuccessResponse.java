package com.io.linkapp.link.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SuccessResponse {
    private int status;
    private String message;
}
