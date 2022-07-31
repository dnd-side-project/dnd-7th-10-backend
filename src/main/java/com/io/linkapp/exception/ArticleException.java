package com.io.linkapp.exception;

public abstract class ArticleException extends RuntimeException{

    public ArticleException(String message) {
        super(message);
    }

    public abstract int statusCode();

}
