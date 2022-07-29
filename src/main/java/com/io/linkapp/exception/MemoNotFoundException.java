package com.io.linkapp.exception;

import lombok.Getter;

@Getter
public class MemoNotFoundException extends MemoException{

    private static final String MESSAGE = "존재하지 않는 메모입니다.";

    public MemoNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int statusCode() {
        return 404;
    }
}
