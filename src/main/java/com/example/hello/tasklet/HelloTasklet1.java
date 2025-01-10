package com.example.hello.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.core.scope.context.ChunkContext;

@Component("HelloTasklet1")
@StepScope
@Slf4j
public class HelloTasklet1 implements Tasklet{

    @Value("#{jobParameters['param1']}")
    private String param1;

    @Value("#{jobParameters['param2']}")
    private Integer param2;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        // taskletの実行確認のためのログ
        log.info("Hello, Tasklet1!");

        // パラメータの値を確認
        log.info("param1={}", param1);
        log.info("param2={}", param2);

        // tasklet2に渡す値を設定
        ExecutionContext jobContext = contribution.getStepExecution()
                                                    .getJobExecution()
                                                    .getExecutionContext();
        // "jobKey1"というキーで値を設定
        jobContext.put("jobKey1", "givenValue1");


        return RepeatStatus.FINISHED;
    }
    
}
