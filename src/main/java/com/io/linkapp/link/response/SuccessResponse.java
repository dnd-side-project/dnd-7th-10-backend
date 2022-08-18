package com.io.linkapp.link.response;

import lombok.Builder;

@Builder
public class SuccessResponse {
    private int status;
    private String message;
}
