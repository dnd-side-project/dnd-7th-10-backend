package com.io.linkapp.link.controller;

import com.io.linkapp.exception.MemoException;
import com.io.linkapp.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ResponseBody
    @ExceptionHandler(MemoException.class)
    public ResponseEntity<ErrorResponse> memoException(MemoException memoException) {
        int statusCode = memoException.statusCode();
        String message = memoException.getMessage();

        ErrorResponse errorResponseBody = ErrorResponse.builder()
                .code(statusCode)
                .message(message)
                .build();

        return ResponseEntity.status(statusCode).body(errorResponseBody);
    }
}
