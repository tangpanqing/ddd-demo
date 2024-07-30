package com.example.test_ddd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.test_ddd.infra.mapper")
public class TestDddApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestDddApplication.class, args);
    }

}
