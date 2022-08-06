package com.io.linkapp.exception;

public class AuthorizeForbiddenException extends GlobalException{

    public static final String MESSAGE = "권한이 없습니다";

    public AuthorizeForbiddenException(){
        super(MESSAGE);
    }

    @Override
    public int statusCode() {
        return 403;
    }
}
