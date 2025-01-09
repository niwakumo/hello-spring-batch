package com.example.hello.validator;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;

public class HelloJobParametersValidator implements JobParametersValidator{

    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException{
        // パラメータ1の値チェック
        String param1 = parameters.getString("param1");
        if(!param1.equals("DEV") 
        && !param1.equals("TEST") 
        && !param1.equals("PROD")){
            throw new JobParametersInvalidException("param1:" + param1 + " DEV/TEST/PRODのいずれかを指定してください");
        }

        // パラメータ2の値チェック
        String param2 = parameters.getString("param2");

        // 数値であることをチェック
        try {
            Integer.parseInt(param2);
        } catch (Exception e) {
            throw new JobParametersInvalidException("param2:" + param2 + " 数値を指定してください");
        }
    }

}
