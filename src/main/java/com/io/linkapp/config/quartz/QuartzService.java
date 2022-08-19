package com.io.linkapp.config.quartz;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class QuartzService {

    private final Scheduler scheduler;
    
    public void init(){
        try{
            //스케줄러 초기화 -> db도 클리어
            scheduler.clear();
            //스케줄러에 job 리스너 등록
            scheduler.getListenerManager().addJobListener(new QuartzJobListener());
            //스케줄러에 trigger 리스너 등록
            scheduler.getListenerManager().addTriggerListener(new QuartzTriggerListener());
            
            //job에 필요한 파라미터 생성
            Map paramsMap = new HashMap<>();
            //job의 실행횟수 및 실행시간
            paramsMap.put("executeCount",1);
            paramsMap.put("date", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            
            //job 생성 및 Scheduler에 등록
            addJob(QuartzJob.class,"QuartzJob","Quartz Job입니다",paramsMap,"0/5 * * * * ?");
            
            
        }catch(Exception e){
            System.out.println("add job error: "+e);
        }
    }
    
    //job 추가
    public <T extends Job> void addJob(Class<? extends Job> job, String name, String dsec, Map paramsMap, String cron)
        throws SchedulerException {
        JobDetail jobDetail = buildJobDetail(job,name,dsec,paramsMap);
        Trigger trigger = buildCronTrigger(cron);
        if(scheduler.checkExists(jobDetail.getKey())){
            scheduler.deleteJob(jobDetail.getKey());
        }
        scheduler.scheduleJob(jobDetail,trigger);
    }
    
    //jobDetail 생성
    public <T extends Job> JobDetail buildJobDetail(Class<? extends Job> job,String name, String desc,Map paramsMap){
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAll(paramsMap);
        
        return JobBuilder.newJob(job)
            .withIdentity(name)
            .withDescription(desc)
            .usingJobData(jobDataMap)
            .build();
    }
    
    //Trigger 생성
    private Trigger buildCronTrigger(String cronExp){
        return TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(cronExp)).build();
    }
}
