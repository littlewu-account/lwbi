package com.wjy.lwbi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wjy.lwbi.mapper")
public class LwbiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LwbiApplication.class, args);
    }

}
