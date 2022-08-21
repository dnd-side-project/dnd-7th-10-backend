package com.io.linkapp.link.request;

import java.util.List;
import java.util.UUID;
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
    private String modifiedCron;
    private String mode;
    private List<UUID> articleIds;
    //private String title;
    //private String body;

}
