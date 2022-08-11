package com.io.linkapp.exception;

public class FolderNotFoundException extends GlobalException{

    public static final String MESSAGE = "폴더를 찾을 수 없습니다";

    public FolderNotFoundException(){
        super(MESSAGE);
    }

    @Override
    public int statusCode() {
        return 404;
    }
}
