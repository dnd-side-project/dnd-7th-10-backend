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
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class QuartzService {

    private final Scheduler scheduler;
    
    public void init(){
        System.out.println("quartzService.init()");
        try{
            
            //scheduler.deleteJob(new JobKey("QuartzJob")); //스케쥴러에 이미 등록된 job 삭제하기
            // 스케쥴러에 job이 한 번 등록되면  spring boot 를 아예 끄고 나도 상태가 유지됨.. 당황,,,
           
            
            //스케줄러 초기화 -> db도 클리어
            //스케쥴러에 job이 한 번 등록되면  spring boot 를 아예 끄고 나도 상태가 유지됨
            //따라서 스케쥴러를 확 초기화해줘야 함
            scheduler.clear();
            System.out.println("scheduler clear");
            //스케줄러에 job 리스너 등록
            scheduler.getListenerManager().addJobListener(new QuartzJobListener());
            System.out.println("add jobListener to scheduler");
            //스케줄러에 trigger 리스너 등록
            scheduler.getListenerManager().addTriggerListener(new QuartzTriggerListener());
            System.out.println("add triggerListener to scheduler");
            
            //job에 필요한 파라미터 생성
            Map paramsMap = new HashMap<>();
            //job의 실행횟수 및 실행시간
            paramsMap.put("executeCount",1);
            paramsMap.put("date", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            
            System.out.println("paramsMap created ");
            
            //job 생성 및 Scheduler에 등록
            addJob(QuartzJob.class,"QuartzJob","quartz",paramsMap,"0 0/5 * * * ?");
            System.out.println("addJob and register to scheduler");
    
    
        }catch(Exception e){
            System.out.println("add job error: "+e);
        }
    }
    
    //job 추가
    public <T extends Job> void addJob(Class<? extends Job> job, String name, String desc, Map paramsMap, String cron)
        throws SchedulerException {
        JobDetail jobDetail = buildJobDetail(job,name,desc,paramsMap);
        Trigger trigger = buildCronTrigger(cron);
        
        System.out.println("cratead jobDetail and trigger");
        
        if(scheduler.checkExists(jobDetail.getKey())){
            System.out.println("already existed job");
            scheduler.deleteJob(jobDetail.getKey());
        }
        
        scheduler.scheduleJob(jobDetail,trigger);
        System.out.println("schedule created job");
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
