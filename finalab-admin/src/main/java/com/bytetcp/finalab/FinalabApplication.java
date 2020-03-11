package com.bytetcp.finalab;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 *
 * @author ruoyi
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@MapperScan({"com.bytetcp.finalab.*.mapper", "com.bytetcp.finalab.serve.*.mapper"})
public class FinalabApplication {
    public static void main(String[] args) {
        SpringApplication.run(FinalabApplication.class, args);
        System.out.println("init project success!");
    }
}