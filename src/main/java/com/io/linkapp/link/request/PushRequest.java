package com.io.linkapp.link.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class PushRequest {
    
    private String targetToken;
    private String title;
    private String body;

}
