package com.io.linkapp.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse {
    
    private int status;
    private String error;
    private String code;
    private String message;
    
    @Builder
    public ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getStatus().value();
        this.error = errorCode.getStatus().name();
        this.code = errorCode.name();
        this.message = errorCode.getMessage();
    }

    public ErrorResponse(String defaultMessage) {
        this.message = defaultMessage;
    }
}
