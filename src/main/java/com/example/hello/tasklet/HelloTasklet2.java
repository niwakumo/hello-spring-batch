package com.example.hello.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("HelloTasklet2")
@StepScope
public class HelloTasklet2 implements Tasklet{

    @Value("#{jobExecutionContext['jobKey1']}")
    private String jobValue1;

    @Override
    public RepeatStatus execute (StepContribution contribution, ChunkContext chunkContext) throws Exception {

        // taskletの実行確認のためのログ
        System.out.println("Hello, Tasklet2!");
        
        // jobKey1の値を確認
        System.out.println("jobValue1= " + jobValue1);

        return RepeatStatus.FINISHED;
    }
}
