package com.io.linkapp.config.security.jwt;

public interface JwtProperty {
    public static final String SUBJECT = "Linkkle";
    public static final int EXPIRATION_TIME = 6000000;
    public static final String SECRET = "LINNKLE";
    public static final String HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
}
