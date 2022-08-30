package com.io.linkapp.config.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerListener;

@Slf4j
//trigger 실행 전후에 event 걸기
public class QuartzTriggerListener implements TriggerListener {
    
    @Override
    public String getName() {
        return this.getClass().getName();
    }
    
    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        log.info("Trigger Start");
    }
    
    /**
     * vetoJobExecution의 결과가 true이면 JobListener의 jobExecutionVetoed 실행.
     * 일단 그냥 중단 없이 쭉 진행되도록 false 리턴
     * @param trigger
     *          The <code>Trigger</code> that has fired.
     * @param context
     *          The <code>JobExecutionContext</code> that will be passed to
     *          the <code>Job</code>'s<code>execute(xx)</code> method.
     * @return
     */
    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        log.info("Health Check on Trigger");
        return false; //false면 중단 없는 것 , true이면 job 중단
    }
    
    @Override
    public void triggerMisfired(Trigger trigger) {
    }
    
    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context,
        CompletedExecutionInstruction triggerInstructionCode) {
        log.info("Complete Trigger");
    }
}
