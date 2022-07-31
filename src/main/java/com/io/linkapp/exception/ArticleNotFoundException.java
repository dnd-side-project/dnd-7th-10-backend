package com.io.linkapp.exception;

import lombok.Getter;

@Getter
public class ArticleNotFoundException extends ArticleException{

    private static final String MESSAGE = "존재하지 않는 기사입니다.";

    public ArticleNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int statusCode() {
        return 404;
    }
}
