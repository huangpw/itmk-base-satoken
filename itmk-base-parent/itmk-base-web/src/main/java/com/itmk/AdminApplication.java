package com.itmk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * @Author: AlbertHPW
 * @Date: 2024/11/22 2:08
 */
@SpringBootApplication
// springboot禁用security，不需要登陆
//@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
