package com.io.linkapp.config.quartz;

import com.io.linkapp.exception.CustomGlobalException;
import com.io.linkapp.exception.ErrorCode;
import com.io.linkapp.link.domain.Article;
import com.io.linkapp.link.domain.QRemind;
import com.io.linkapp.link.domain.Remind;
import com.io.linkapp.link.repository.ArticleRepository;
import com.io.linkapp.link.repository.RemindRepository;
import com.io.linkapp.link.request.PushRequest;
import com.io.linkapp.user.domain.User;
import com.querydsl.core.BooleanBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class QuartzService {

    private final Scheduler scheduler;
    
    private final RemindRepository remindRepository;
    
    private final ArticleRepository articleRepository;
    
    public void resetScheduler(User user) throws SchedulerException {
        UUID userId = user.getId();
        
        Remind defaultRemind = remindRepository.findOne(new BooleanBuilder(QRemind.remind.userId.eq(userId)
            .and(QRemind.remind.remindTitle.eq("default")))).orElse(null);
        
        if(defaultRemind != null){
            //그러면 리마인드 객체도 모두 없애주면서 그 안의 article들 모두 default 리마인드로 옮겨야 함
            //1. 그 안의 article들 모두 default 리마인드로
            List<Remind> remindList = (List<Remind>) remindRepository.findAll(new BooleanBuilder(QRemind.remind.userId.eq(userId).and(
                QRemind.remind.remindTitle.ne("default")
            )));
            for(int i=0;i<remindList.size();i++){
                Remind remind = remindList.get(i);
                List<Article> articleList = remind.getArticleList();
                for(int j=0;j<articleList.size();j++){
                    Article article = articleList.get(j);
                    article.setRemindId(defaultRemind.getRemindId());
                    articleRepository.save(article);
                }
            }
    
            //2. 리마인드 객체 모두 없애기 - default 제외
            remindRepository.deleteAll(remindList);
    
            scheduler.clear();
            log.info("Cleared");
        }
    }
    
    public Remind initSchedule(PushRequest pushRequest, User user){
        log.info("Quartz Service Init");
        try{
            //job 삭제되면 trigger도 삭제됨 - 직접 테스트, db 확인해서 확실하게 확인함
            String mode; //지금 해당 알람 추가인지 삭제인지 수정인지
            
            if(pushRequest.getMode().equals("delete")){
                mode = "delete";
            }else if(pushRequest.getMode().equals("modify")){
                mode ="modify";
            }else{
                mode ="add";
            }

            log.info("mode : {}", mode);
            
            // 따라서 기존 알람 시간을 삭제하고 싶담뎐 - 수정 정보에 기존과 똑같은 cron을 넣어주면 됨
            if(mode =="delete" || mode =="modify"){
                if(scheduler.checkExists(new JobKey( "QuartzJob"+user.getId().toString()+pushRequest.getCron()))){
                    //그리고 그 리마인드를 삭제
                    Remind deletedRemind = remindRepository.findOne(new BooleanBuilder(QRemind.remind.userId.eq(user.getId())
                        .and(QRemind.remind.cron.eq(pushRequest.getCron())))).orElse(null);
                    // 그 리마인드의 아티클들 default로 옮김
                    Remind defaultRemind = remindRepository.findOne(new BooleanBuilder(QRemind.remind.userId.eq(user.getId())
                        .and(QRemind.remind.remindTitle.eq("default")))).orElse(null);
                    List<Article> articles = deletedRemind.getArticleList();
                    for(int j=0;j<articles.size();j++){
                        Article article = articles.get(j);
                        article.setRemindId(defaultRemind.getRemindId());
                        articleRepository.save(article);
                    }
                    if(deletedRemind !=null){
                        remindRepository.delete(deletedRemind);
                    }
                    
                    scheduler.deleteJob(new JobKey( "QuartzJob"+user.getId().toString()+pushRequest.getCron()));
                    scheduler.start();
                    log.info("mode: {}", mode, " delete current job");
                }
            }
            
            if(mode != "delete"){
                //스케줄러에 job 리스너 등록
                scheduler.getListenerManager().addJobListener(new QuartzJobListener());
                log.info("Add JobListener to Scheduler");
                //스케줄러에 trigger 리스너 등록
                scheduler.getListenerManager().addTriggerListener(new QuartzTriggerListener());
                log.info("Add TriggerListener to Scheduler");
    
                //job에 필요한 파라미터 생성 - () 안에 넣는 게 아니라 이런 식으로 전달해야 함
                Map paramsMap = new HashMap<>();
                // 파라미터를 map으로 넘겨주는 거임
                paramsMap.put("targetToken",pushRequest.getTargetToken());
                paramsMap.put("userId",user.getId());
                paramsMap.put("articleIds",pushRequest.getArticleIds()); //uuid로 넘어감

                String cron="";
    
                if(mode =="modify"){ //시간 정보가 수정되는 것이라면
                    cron= pushRequest.getModifiedCron();
                
                }else if(mode =="add"){ //그냥 최초의 시간 정보라면 (시간 수정이 아니라면)
                    cron = pushRequest.getCron();
                }
    
                //알람 시간에 따른 해당 리마인드 객체 생성
                Remind remind = Remind.builder()
                    .userId(user.getId())
                    .cron(cron)
                    .remindTitle("remindFor "+cron)
                    .build();
    
                Remind savedRemind = remindRepository.save(remind);
    
                //그리고 요청 받은 아티클들의 remindId 업데이트 - 원래는 default에만 있었음
                for(int i=0;i<pushRequest.getArticleIds().size();i++){
                    Article article = articleRepository.findById(pushRequest.getArticleIds().get(i))
                        .orElseThrow(() -> new CustomGlobalException(ErrorCode.ARTICLE_NOT_FOUND));
                    article.setRemindId(savedRemind.getRemindId());
                    articleRepository.save(article);
                }
    
                //job 생성 및 Scheduler에 등록
                //넘겨받은 사용자 정보로 remind 시간 cron으로 변환해서 넣기
    
                if(mode =="modify"){ //시간 정보가 수정되는 것이라면
                    String jobName = "QuartzJob"+user.getId().toString()+pushRequest.getModifiedCron(); //유저마다, 또 해당 시간마다 jobName이 모두 다르도록- 그래서 key로 구분될 수 있도록
                    addJob(QuartzJob.class,jobName,"quartz",paramsMap, pushRequest.getModifiedCron());
                    log.info("Modified Cron Job");
                    return savedRemind;
                }else if(mode =="add"){ //그냥 최초의 시간 정보라면 (시간 수정이 아니라면)
                    String jobName = "QuartzJob"+user.getId().toString()+pushRequest.getCron(); //유저마다, 또 해당 시간마다 jobName이 모두 다르도록- 그래서 key로 구분될 수 있도록
                    addJob(QuartzJob.class,jobName,"quartz",paramsMap, pushRequest.getCron());
                    log.info("AddJob and Register to Scheduler");
                    return savedRemind;
                }
            }
            
            
        }catch(Exception e){
           log.error("Add job error = {}", e);
        }
        
        return null;
    }
    
    //job 추가
    public <T extends Job> void addJob(Class<? extends Job> job, String name, String desc, Map paramsMap, String cron)
        throws SchedulerException {
        JobDetail jobDetail = buildJobDetail(job,name,desc,paramsMap);
        Trigger trigger = buildCronTrigger(cron,name+"trigger");
        
        log.info("Created JobDetail and Trigger");
        
        if(scheduler.checkExists(jobDetail.getKey())){
            log.info("Already Existed Job");
            scheduler.deleteJob(jobDetail.getKey());
        }
        
        scheduler.scheduleJob(jobDetail,trigger);
        scheduler.start();
        log.info("Schedule Created Job");
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
