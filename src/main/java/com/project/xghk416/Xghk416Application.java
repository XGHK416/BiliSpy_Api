package com.project.xghk416;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("com.project.xghk416.pojo.dao") //扫描的mapper
@SpringBootApplication
public class Xghk416Application {

    public static void main(String[] args) {
        SpringApplication.run(Xghk416Application.class, args);
    }

}
