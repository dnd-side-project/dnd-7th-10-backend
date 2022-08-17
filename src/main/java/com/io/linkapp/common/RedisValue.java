package com.io.linkapp.common;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RedisValue {
    private String key;
    private String value;
}
