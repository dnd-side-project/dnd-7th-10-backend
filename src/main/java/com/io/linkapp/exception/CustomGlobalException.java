package com.io.linkapp.exception;

import lombok.Getter;

@Getter
public class CustomGlobalException extends RuntimeException {

    private final ErrorCode errorCode;
    
    public CustomGlobalException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }
}
