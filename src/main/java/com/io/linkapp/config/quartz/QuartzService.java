package com.io.linkapp.config.quartz;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.io.linkapp.config.security.auth.PrincipalDetails;
import com.io.linkapp.exception.CustomGlobalException;
import com.io.linkapp.link.request.PushRequest;
import com.io.linkapp.user.domain.User;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Configuration
@RequiredArgsConstructor
public class QuartzService {

    private final Scheduler scheduler;
    
    public void resetScheduler() throws SchedulerException {
        scheduler.clear();
        System.out.println("cleared");
    }
    
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
            //넘겨받은 사용자 정보로 remind 시간 cron으로 변환해서 넣기
            addJob(QuartzJob.class,"QuartzJob","quartz",paramsMap,"0 0/5 * * * ?");
            System.out.println("addJob and register to scheduler");
    
    
        }catch(Exception e){
            System.out.println("add job error: "+e);
        }
    }
    
    
    public void init2(PushRequest pushRequest, User user){
        System.out.println("quartzService.init2()");
        try{
            
            //scheduler.deleteJob(new JobKey("QuartzJob")); //스케쥴러에 이미 등록된 job 삭제하기
            // 스케쥴러에 job이 한 번 등록되면  spring boot 를 아예 끄고 나도 상태가 유지됨.. 당황,,,
            
            //두 번째 누른다는 거는 시간 정보 등을 수정하기 위한 것이므로 삭제하고 job을 새로 넣어줌
            //job 삭제되면 trigger도 삭제됨 - 직접 테스트, db 확인해서 확실하게 확인함
            if(scheduler.checkExists(new JobKey( "QuartzJob"+user.getId().toString()))){
                scheduler.deleteJob(new JobKey( "QuartzJob"+user.getId().toString()));
                System.out.println("delete curr User Job");
            }
    
            if(scheduler.checkExists(new JobKey( "QuartzJob"))){
                scheduler.deleteJob(new JobKey( "QuartzJob"));
                System.out.println("delete default Job");
            }
            
            //스케줄러에 job 리스너 등록
            scheduler.getListenerManager().addJobListener(new QuartzJobListener());
            System.out.println("add jobListener to scheduler");
            //스케줄러에 trigger 리스너 등록
            scheduler.getListenerManager().addTriggerListener(new QuartzTriggerListener());
            System.out.println("add triggerListener to scheduler");
            
            //job에 필요한 파라미터 생성 - () 안에 넣는 게 아니라 이런 식으로 전달해야 함
            Map paramsMap = new HashMap<>();
            //job의 실행횟수 및 실행시간
            paramsMap.put("targetToken",pushRequest.getTargetToken());
            paramsMap.put("userId",user.getId());
            
            System.out.println("job paramsMap created ");
            
            //job 생성 및 Scheduler에 등록
            //넘겨받은 사용자 정보로 remind 시간 cron으로 변환해서 넣기
            String jobName = "QuartzJob"+user.getId().toString(); //유저마다 jobName이 모두 다르도록- 그래서 key로 구분될 수 있도록
            addJob(QuartzJob.class,jobName,"quartz",paramsMap, pushRequest.getCron());
            System.out.println("addJob and register to scheduler");
            
            
        }catch(Exception e){
            System.out.println("add job error: "+e);
        }
    }
    
    //job 추가
    public <T extends Job> void addJob(Class<? extends Job> job, String name, String desc, Map paramsMap, String cron)
        throws SchedulerException {
        JobDetail jobDetail = buildJobDetail(job,name,desc,paramsMap);
        Trigger trigger = buildCronTrigger(cron,name+"trigger");
        
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
            .withIdentity(name) //즉 job name이 key 역할을 해줌
            .withDescription(desc)
            .usingJobData(jobDataMap)
            .build();
    }
    
    //Trigger 생성
    private Trigger buildCronTrigger(String cronExp,String name){
        return TriggerBuilder.newTrigger().
            withIdentity(name).withSchedule(CronScheduleBuilder.cronSchedule(cronExp)).build();
    }
}
