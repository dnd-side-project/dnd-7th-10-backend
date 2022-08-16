package com.io.linkapp.exception;

public class RefreshTokenNotFoundException extends RuntimeException{

    public static final String message = "리프레쉬 토큰을 찾을 수 없습니다.";

    public RefreshTokenNotFoundException() {
        super(message);
    }
}
