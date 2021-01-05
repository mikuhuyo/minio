package com.minio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author yuelimin
 * @since 1.8
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MinIoApplication {
    public static void main(String[] args) {
        SpringApplication.run(MinIoApplication.class);
    }
}
