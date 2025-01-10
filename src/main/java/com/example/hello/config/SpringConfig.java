package com.example.hello.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.hello.validator.HelloJobParametersValidator;

import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;


@Configuration
public class SpringConfig {
    private final JobLauncher jobLauncher;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    // タスクレットの設定
    @Autowired
    @Qualifier("HelloTasklet1") // tasklet1のBean名を指定して区別する
    private Tasklet helloTasklet1;

    @Autowired
    @Qualifier("HelloTasklet2")
    private Tasklet helloTasklet2;

    // Chunkの設定
    @Autowired
    private ItemReader<String> helloReader;

    @Autowired
    private ItemProcessor<String, String> helloProcessor;

    @Autowired
    private ItemWriter<String> helloWriter;

    // listenerの設定
    @Autowired
    private JobExecutionListener helloJobExecutionListener;

    // コンストラクタ
    public SpringConfig(JobLauncher jobLauncher, JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        this.jobLauncher = jobLauncher;
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    // バリデーションの設定
    @Bean
    public JobParametersValidator jobParametersValidator() {
        return new HelloJobParametersValidator();
    }

    // ステップの設定(タスクレット)
    @Bean
    public Step helloTaskletStep1() {
        return new StepBuilder("helloTaskletStep1", jobRepository)
            .tasklet(helloTasklet1, transactionManager) // タスクレットの設定
            .build();
    }

    @Bean
    public Step helloTaskletStep2() {
        return new StepBuilder("helloTaskletStep2", jobRepository)
            .tasklet(helloTasklet2, transactionManager) // タスクレットの設定
            .build();
    }

    // ステップの設定(Chunk)
    @Bean
    public Step helloChunkStep() {
        return new StepBuilder("helloChunkStep", jobRepository)
                    .<String, String>chunk(3, transactionManager) // 第1引数で処理する件数を設定
                    .reader(helloReader)
                    .processor(helloProcessor)
                    .writer(helloWriter)
                    .build();
    }

    // ジョブの設定
    @Bean
    public Job helloJob() {
        return new JobBuilder("helloJob", jobRepository)
            .incrementer(new RunIdIncrementer()) // ジョブの実行IDをインクリメント
            .start(helloTaskletStep1()) // タスクレットステップの設定
            .next(helloTaskletStep2()) 
            .next(helloChunkStep()) // チャンクステップの設定
            .validator(jobParametersValidator()) // バリデーションの設定
            .listener(helloJobExecutionListener) // リスナーの設定
            .build();

    }
}
