package com.example.hello.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.batch.core.scope.context.ChunkContext;

@Component("HelloTasklet1")
@StepScope
public class HelloTasklet1 implements Tasklet{

    @Value("#{jobParameters['param1']}")
    private String param1;

    @Value("#{jobParameters['param2']}")
    private Integer param2;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        // taskletの実行確認のためのログ
        System.out.println("Hello, Tasklet1!");

        // パラメータの値を確認
        System.out.println("param1= " + param1);
        System.out.println("param2= " + param2);
        return RepeatStatus.FINISHED;
    }
    
}
