package com.io.linkapp.exception;

//TODO : Not Use, But Consider
public abstract class MemoException extends RuntimeException{

    public MemoException(String message) {
        super(message);
    }

    public abstract int statusCode();
}
