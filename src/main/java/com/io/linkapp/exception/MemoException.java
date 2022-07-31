package com.io.linkapp.exception;

public abstract class MemoException extends RuntimeException{

    public MemoException(String message) {
        super(message);
    }

    public abstract int statusCode();
}
