package com.io.linkapp.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomGlobalException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;
    private final HttpStatus status;
    
    public CustomGlobalException(ErrorCode errorCode){
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
        this.status = errorCode.getStatus();
    }
}
