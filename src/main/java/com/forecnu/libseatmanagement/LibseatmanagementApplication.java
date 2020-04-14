package com.forecnu.libseatmanagement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author wuwc
 * @version 1.0
 * @date 2020/4/14 17:26
 */
@MapperScan("com.forecnu.libseatmanagement.dao")
@SpringBootApplication
@EnableScheduling
public class LibseatmanagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibseatmanagementApplication.class, args);
    }

}
