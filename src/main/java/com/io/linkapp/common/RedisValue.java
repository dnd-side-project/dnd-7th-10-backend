package com.io.linkapp.common;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RedisValue {
    private String key;
    private String value;
    private String expireTime;

    @Builder
    public RedisValue(String key, String value, String expireTime) {
        this.key = key;
        this.value = value;
        this.expireTime = expireTime + "초";
    }

}
