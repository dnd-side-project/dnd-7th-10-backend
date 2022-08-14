//package com.io.linkapp.exception;
//
//import com.io.linkapp.link.response.ErrorResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.*;
//
//@Slf4j
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(GlobalException.class)
//    public ResponseEntity<ErrorResponse> handleGlobalException(GlobalException globalException) {
//        int statusCode = globalException.statusCode();
//        String message = globalException.getMessage();
//
//        ErrorResponse errorResponseBody = ErrorResponse.builder()
//                .code(statusCode)
//                .message(message)
//                .build();
//
//        return ResponseEntity.status(statusCode).body(errorResponseBody);
//    }
//
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ErrorResponse handleNotBlankValid(MethodArgumentNotValidException e){
//        ErrorResponse errorResponse = ErrorResponse.builder()
//                .code(400)
//                .message(e.getFieldError().getDefaultMessage())
//                .build();
//
//        return errorResponse;
//    }
//}
