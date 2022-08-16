package com.io.linkapp.exception;

public class RefreshTokenNotValidateException extends RuntimeException{

    public static final String message = "리프레쉬 토큰이 유효하지않습니다.";

    public RefreshTokenNotValidateException() {
        super(message);
    }

}
