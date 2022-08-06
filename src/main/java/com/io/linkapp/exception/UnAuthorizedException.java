package com.io.linkapp.exception;

public class UnAuthorizedException extends GlobalException{

    public static final String MESSAGE = "로그인이 실패하였습니다.";

    public UnAuthorizedException(){
        super(MESSAGE);
    }

    @Override
    public int statusCode() {
        return 401;
    }
}
