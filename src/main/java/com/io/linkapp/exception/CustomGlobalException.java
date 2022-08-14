package com.io.linkapp.exception;

import com.io.linkapp.exception.form.ErrorCode;
import lombok.Getter;

// ErrorCode에 직접 정의한 Custom 예외를 처리할 Exception 클래스

@Getter
public class CustomGlobalException extends RuntimeException {

    private final ErrorCode errorCode;
    
    //생성자 - errorCode가 있어야만 생성될 수 있게 함
    public CustomGlobalException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }
    
}
