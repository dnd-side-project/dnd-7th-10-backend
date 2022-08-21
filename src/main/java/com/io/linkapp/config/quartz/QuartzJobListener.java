package com.io.linkapp.config.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

//jobListener는 job 실행 전후에 event를 걸어주는 역할 담당
public class QuartzJobListener implements JobListener {
    
    @Override
    public String getName() {
        return this.getClass().getName();
    }
    
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        
        System.out.println("job 수행되기 전");
    }
    
    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        System.out.println("job 중단");
    }
    
    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        System.out.println("job 수행 완료 후");
    }
}
