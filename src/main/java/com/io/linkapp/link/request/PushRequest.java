package com.io.linkapp.link.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PushRequest {
    
    private String targetToken;
    private String cron;
    //private String title;
    //private String body;

}
