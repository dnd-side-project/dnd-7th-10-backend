package com.io.linkapp.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomGlobalExceptionHandler {
    
    @ExceptionHandler(CustomGlobalException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(final CustomGlobalException globalException){
        log.error("Custom Exception!", globalException);
        return ResponseEntity.status(globalException.getErrorCode().getStatus().value())
            .body(new ErrorResponse(globalException.getErrorCode()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e) {
        log.error("Method Not Supported!", e);
        return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus().value())
            .body(new ErrorResponse(ErrorCode.METHOD_NOT_ALLOWED));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleNotBlankValid(MethodArgumentNotValidException e){
        log.error("NotValid!", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
            .body(new ErrorResponse(e.getFieldError().getDefaultMessage()));
    }
    
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Error!", e);
        return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus().value())
            .body(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR));
    }
}
