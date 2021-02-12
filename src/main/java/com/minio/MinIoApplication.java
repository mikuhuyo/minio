package com.minio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author yuelimin
 * @since 1.8
 */
@EnableTransactionManagement
@SpringBootApplication(exclude = {MongoDataAutoConfiguration.class})
public class MinIoApplication {
    public static void main(String[] args) {
        SpringApplication.run(MinIoApplication.class);
    }
}
