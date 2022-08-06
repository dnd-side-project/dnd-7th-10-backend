package com.io.linkapp.exception;

public class UserNotFoundException extends GlobalException{

    public static final String MESSAGE = "존재하지 않는 사용자입니다.";

    public UserNotFoundException(){
        super(MESSAGE);
    }

    @Override
    public int statusCode() {
        return 404;
    }
}
