package com.example.hello.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class HelloJobExecutionListener implements JobExecutionListener{

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("ジョブ開始時刻: {}", jobExecution.getStartTime());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("ジョブ終了時刻: {}", jobExecution.getEndTime());
    }

}
