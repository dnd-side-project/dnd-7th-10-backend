package com.io.linkapp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

    POSTS_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 요청의 리소스를 찾을 수 없습니다."),
    
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 메서드입니다."),
    
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류입니다."),

    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 아티클입니다."),

    FOLDER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 폴더입니다."),

    MEMO_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 메모입니다."),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),

    AUTHORIZE_FORBIDDEN(HttpStatus.FORBIDDEN, "권한이 없습니다."),

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "로그인이 실패하였습니다.");
    
    private final HttpStatus status;
    private final String message;
}
