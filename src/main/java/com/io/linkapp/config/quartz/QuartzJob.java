package com.io.linkapp.config.quartz;

import com.io.linkapp.link.domain.Market;
import com.io.linkapp.link.repository.MarketRepository;
import com.io.linkapp.link.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@PersistJobDataAfterExecution //job이 동작 중에 dataMap을 변경할 때 사용
@DisallowConcurrentExecution
//Job은 @RequiredArgsConstructor 사용못함
public class QuartzJob implements Job {
    
    @Autowired
    private MarketRepository marketRepository;
    
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
    
        System.out.println("Quartz Job Executed");
    
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        System.out.println("dataMap date: "+dataMap.get("date"));
        System.out.println("dataMap executeCount: "+dataMap.get("executeCount"));
    
        //JobDataMap을 통해 job의 실행 횟수를 받아서 그 횟수+1을 해준다
        int cnt = (int) dataMap.get("executeCount");
        dataMap.put("executeCount",++cnt);
        
        //marekt 테이블에 insert
        Market market = new Market();
        market.setMarketName(dataMap.get("date").toString());
        market.setMarketPrice("3000");
        marketRepository.save(market);
        
    }
}
