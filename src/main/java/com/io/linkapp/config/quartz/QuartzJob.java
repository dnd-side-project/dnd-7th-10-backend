package com.io.linkapp.config.quartz;

import com.io.linkapp.link.service.FirebaseCloudMessageService;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@PersistJobDataAfterExecution //job이 동작 중에 dataMap을 변경할 때 사용
@DisallowConcurrentExecution
//Job은 @RequiredArgsConstructor 사용못함
public class QuartzJob implements Job {
    
    @Autowired
    private FirebaseCloudMessageService firebaseCloudMessageService;
    
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
    
        System.out.println("Quartz Job Executed");
        
        //jobDetail에서 생성해준 parmasMap
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
    
        //단순 확인용 - 나중에 아래의 4줄은 없애야 함
        UUID userId = (UUID) dataMap.get("userId");
        String targetToken = (String) dataMap.get("targetToken");
        List<UUID> articleIds = (List<UUID>) dataMap.get("articleIds");
        System.out.println("dataMap targetToken: "+targetToken);
        System.out.println("dataMap userId: "+userId);
        System.out.println("dataMap articleIds: "+articleIds);
        

        try {
            firebaseCloudMessageService.sendMessageTo(userId,targetToken,articleIds);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        
    }
}
