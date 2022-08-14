package com.io.linkapp.exception.form;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
//@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class ErrorResponse {
    
    //private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final String code;
    private final String message;
    
    @Builder
    public ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getStatus().value();
        this.error = errorCode.getStatus().name();
        this.code = errorCode.name(); //열거 객체의 문자열 리턴
        this.message = errorCode.getMessage();
    }
    
}
