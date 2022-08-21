package com.io.linkapp.config.quartz;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

//QuartzJob은 requiredArgsConstructor를 사용 못하므로 얘를 통해서 설정해준다
//https://biggwang.github.io/2018/12/05/ETC/[quartz]%20%EC%8B%A4%EC%8B%9C%EA%B0%84%20%EB%B0%B0%EC%B9%98%20%EB%8F%99%EC%A0%81%20%EC%8A%A4%EC%BC%80%EC%A4%84%EB%9F%AC%20%EC%82%AC%EC%9A%A9%EA%B8%B0/
//위의 링크 참고

//Quartz 스케줄러는 스프링의 컨테이너의 빈 LifeCycle 관리에 의해서 scheduler관련 설정이 초기화, 시작, 종료가 된다
public class AutoWiringSpringBeanJobFactory extends SpringBeanJobFactory implements
    ApplicationContextAware {
    
    //transient = 직렬화 작업에서 제외
    //즉 이터를 디스크에 저장하거나 디비에 저장하거나 http request를 통해 보내거나 할때 민감한 데이터(개인정보등)은 제외하고 싶으면
    //객체를 데이터 스트림에 쓰기 위한 작업을 ** 직렬화 ** 라는 과정을 통해 가능하게 만들어 주는것
    private transient AutowireCapableBeanFactory beanFactory;
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        beanFactory = applicationContext.getAutowireCapableBeanFactory();
    }
    
    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception { //job 객체 생성
       
        final Object job = super.createJobInstance(bundle);
        beanFactory.autowireBean(job);
        return job;
    }
}
