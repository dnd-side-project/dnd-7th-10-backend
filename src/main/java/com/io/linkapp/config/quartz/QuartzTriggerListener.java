package com.io.linkapp.config.quartz;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerListener;

//trigger 실행 전후에 event 걸기
public class QuartzTriggerListener implements TriggerListener {
    
    @Override
    public String getName() {
        return this.getClass().getName();
    }
    
    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        System.out.println("Trigger 실행");
    }
    
    /**
     * vetoJobExecution의 결과가 true이면 JobListener의 jobExecutionVetoed 실행. 즉 중단
     * @param trigger
     *          The <code>Trigger</code> that has fired.
     * @param context
     *          The <code>JobExecutionContext</code> that will be passed to
     *          the <code>Job</code>'s<code>execute(xx)</code> method.
     * @return
     */
    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
    
        System.out.println("trigger 상태 체크");
    
        JobDataMap map = context.getJobDetail().getJobDataMap();
        
        int executeCount = 1;
        
        if(map.containsKey("executeCount")){
            executeCount=(int)map.get("executeCount");
        }
        
        return executeCount>=2;
    }
    
    @Override
    public void triggerMisfired(Trigger trigger) {
    
    }
    
    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context,
        CompletedExecutionInstruction triggerInstructionCode) {
        System.out.println("Trigger 성공");
    }
}
