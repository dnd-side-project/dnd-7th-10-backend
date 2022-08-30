package com.io.linkapp.config.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

@Slf4j
//jobListener는 job 실행 전후에 event를 걸어주는 역할 담당
public class QuartzJobListener implements JobListener {
    
    @Override
    public String getName() {
        return this.getClass().getName();
    }
    
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
      log.info("Before Job, JobToBeExecuted");
    }
    
    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        log.warn("Job Execution Vetoed");
    }
    
    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        log.info("Job was Executed");
    }
}
