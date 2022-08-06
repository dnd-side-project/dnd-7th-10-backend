package com.io.linkapp.exception;

public class MemoNotFoundException extends GlobalException{

    private static final String MESSAGE = "존재하지 않는 메모입니다.";

    public MemoNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int statusCode() {
        return 404;
    }
}
