package com.mallall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(value = "com.mallall.dao")
@SpringBootApplication
public class MallAllApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallAllApplication.class, args);
    }

}
