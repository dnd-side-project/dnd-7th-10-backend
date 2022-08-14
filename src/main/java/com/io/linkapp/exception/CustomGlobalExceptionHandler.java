package com.io.linkapp.exception;


import com.io.linkapp.exception.form.ErrorCode;
import com.io.linkapp.exception.form.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ResponseEntity<ErrorResponse>
 *
 * ResponseEntity<T>는 HTTP Request에 대한 응답 데이터를 포함하는 클래스
 *
 * <Type>에 해당하는 데이터와 HTTP 상태 코드를 함께 리턴
 *
 * 우리는 예외가 발생했을 때, ErrorResponse 형식으로 예외 정보를 Response로 내려주는 것
 */

@RestControllerAdvice
public class CustomGlobalExceptionHandler {
    
    /*
     * Developer Custom Exception
     */
    @ExceptionHandler(CustomGlobalException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(final CustomGlobalException e){
        return ResponseEntity.status(e.getErrorCode().getStatus().value())
            .body(new ErrorResponse(e.getErrorCode()));
    }
    
    
    /*
     * HTTP 405 Exception
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e) {
        return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus().value())
            .body(new ErrorResponse(ErrorCode.METHOD_NOT_ALLOWED));
        
    }
    
    /*
     * HTTP 500 Exception
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(final Exception e) {
        return ResponseEntity
            .status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus().value())
            .body(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR));
    }

}
