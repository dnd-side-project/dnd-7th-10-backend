package com.io.linkapp.exception;

public class ArticleNotFoundException extends GlobalException {

    private static final String MESSAGE = "존재하지 않는 기사입니다.";

    public ArticleNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int statusCode() {
        return 404;
    }
}
