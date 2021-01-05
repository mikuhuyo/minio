package com.minio.task;

import com.minio.utils.MinIoUtil;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Set;

/**
 * @author yuelimin
 * @since 1.8
 */
@Component
public class TimerTaskClearImage {
    @Value("${minio.bucketImageName}")
    private String bucketImageName;
    @Autowired
    private MinIoUtil minIoUtil;
    @Autowired
    private JedisPool jedisPool;

    /**
     * 每间隔五秒钟执行一次
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void testSpringTask() throws IOException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, ServerException, ErrorResponseException, XmlParserException, InvalidBucketNameException, InsufficientDataException, InternalException {
        Set<String> sdiff = jedisPool.getResource().sdiff("trash", "storage");
        int size = sdiff.size();
        if (size != 0) {
            for (String s : sdiff) {
                minIoUtil.deleteFile(bucketImageName, s);
                jedisPool.getResource().srem("trash", s);
            }
        }
    }
}
