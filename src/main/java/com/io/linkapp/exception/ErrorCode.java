package com.io.linkapp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    
    INQUIRY_ALREADY_ANSWERED(HttpStatus.BAD_REQUEST, "이미 답변이 완료된 문의사항입니다"),
    
    REMIND_NO_MORE(HttpStatus.BAD_REQUEST, "사용자 한 명당 한 개의 리마인드만 가질 수 있습니다"),

    NO_ARTICLES_FOR_REMIND(HttpStatus.NOT_FOUND, "현재 리마인딩할 아티클이 없습니다"),

    POSTS_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 요청의 리소스를 찾을 수 없습니다."),
    
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 메서드입니다."),
    
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류입니다."),

    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 아티클입니다."),
    INQUIRY_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 문의사항입니다."),
    
    REMIND_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 리마인드입니다."),
    
    DEFAULT_REMIND_NOT_FOUND(HttpStatus.NOT_FOUND, "디폴트 리마인드가 존재하지 않습니다"),

    FOLDER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 폴더입니다."),

    MEMO_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 메모입니다."),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),

    TAG_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 태그입니다."),

    PARSE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "URL 정보가 잘못되었습니다."),

    REFRESH_NOT_VALID(HttpStatus.NOT_FOUND, "일치하는 리프레시 토큰 정보를 찾을 수 없습니다."),

    TOKEN_NOT_VALID(HttpStatus.NOT_FOUND, "일치하는 액세스 토큰 정보를 찾을 수 없습니다."),

    EXIST_USER(HttpStatus.INTERNAL_SERVER_ERROR, "이미 존재하는 유저 이름입니다."),

    PASSWORD_NOT_MATCH(HttpStatus.UNAUTHORIZED, "패스워드가 일치하지 않습니다.");

    private final HttpStatus status;
    private final String message;
}
