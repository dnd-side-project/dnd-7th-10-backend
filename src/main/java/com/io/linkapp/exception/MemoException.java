package com.io.linkapp.exception;

import lombok.Getter;

public abstract class MemoException extends RuntimeException{

    public MemoException(String message) {
        super(message);
    }

    public abstract int statusCode();
}
