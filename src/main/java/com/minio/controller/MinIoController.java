package com.minio.controller;

import com.minio.domain.domain.CommonResult;
import com.minio.utils.MinIoUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * @author yuelimin
 * @since 1.8
 */
@Api(value = "MinIO服务接口")
@RestController
public class MinIoController {
    @Value("${minio.bucketImageName}")
    private String bucketImageName;
    @Value("${minio.serviceName}")
    private String serviceName;
    @Autowired
    private MinIoUtil minIoUtil;
    @Autowired
    private JedisPool jedisPool;

    // @PostMapping("/image/bucket/{bucketName}")
    // @ApiOperation(value = "新建桶", notes = "新建桶")
    // public CommonResult createBucket(@PathVariable("bucketName") String bucketName) {
    //     // https://docs.aws.amazon.com/AmazonS3/latest/dev/access-policy-language-overview.html
    //     StringBuilder policyJsonBuilder = new StringBuilder();
    //     policyJsonBuilder.append("{\n");
    //     policyJsonBuilder.append("    \"Statement\": [\n");
    //     policyJsonBuilder.append("        {\n");
    //     policyJsonBuilder.append("            \"Action\": [\n");
    //     policyJsonBuilder.append("                \"s3:GetBucketLocation\",\n");
    //     policyJsonBuilder.append("                \"s3:ListBucket\"\n");
    //     policyJsonBuilder.append("            ],\n");
    //     policyJsonBuilder.append("            \"Effect\": \"Allow\",\n");
    //     policyJsonBuilder.append("            \"Principal\": \"*\",\n");
    //     policyJsonBuilder.append("            \"Resource\": \"arn:aws:s3:::" + bucketName + "\"\n");
    //     policyJsonBuilder.append("        },\n");
    //     policyJsonBuilder.append("        {\n");
    //     policyJsonBuilder.append("            \"Action\": \"s3:GetObject\",\n");
    //     policyJsonBuilder.append("            \"Effect\": \"Allow\",\n");
    //     policyJsonBuilder.append("            \"Principal\": \"*\",\n");
    //     policyJsonBuilder.append("            \"Resource\": \"arn:aws:s3:::"
    //             + bucketName + "/project-file*\"\n");
    //     policyJsonBuilder.append("        }\n");
    //     policyJsonBuilder.append("    ],\n");
    //     policyJsonBuilder.append("    \"Version\": \"2012-10-17\"\n");
    //     policyJsonBuilder.append("}\n");
    //
    //     try {
    //         minIoUtil.makeBucket(bucketName);
    //         minIoUtil.setBucketPolicy(bucketName, policyJsonBuilder.toString());
    //         return CommonResult.success();
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    //
    //     return CommonResult.failed(String.format("[%s] 新建失败", bucketName));
    // }

    @PostMapping("/image")
    @ApiOperation(value = "文件上传")
    public CommonResult<String> uploadFile(@RequestParam(value = "file", required = true) MultipartFile file) {
        if (file.getSize() == 0 || file.isEmpty()) {
            return CommonResult.failed("接收了个寂寞");
        }

        try {
            if (!minIoUtil.bucketExists(bucketImageName)) {
                return CommonResult.failed("桶不存在, 请创建");
            }

            // 获取文件全名称
            String originalFilename = file.getOriginalFilename();
            // 获取文件类型
            String contentType = file.getContentType();
            // 获取输入流
            InputStream inputStream = file.getInputStream();
            // 存储文件名称
            assert originalFilename != null;
            String newFileName = serviceName
                    + "/"
                    + UUID.randomUUID().toString().replaceAll("-", "")
                    + originalFilename.substring(originalFilename.lastIndexOf("."));

            minIoUtil.putObject(bucketImageName, newFileName, contentType, inputStream);
            inputStream.close();
            // 获取访问url路径
            String objectUrl = minIoUtil.getObjectUrl(bucketImageName, newFileName);
            jedisPool.getResource().sadd("storage", objectUrl);

            return CommonResult.success(objectUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return CommonResult.failed();
    }

    @DeleteMapping("/image/{imageUrl}")
    @ApiOperation(value = "删除文件")
    @ApiImplicitParam(name = "imageUrl", value = "文件URL地址", required = true, dataType = "string", paramType = "query")
    public CommonResult deleteImage(@PathVariable("imageUrl") String url) {

        String imageUrl = url;
        try {
            minIoUtil.deleteFile(bucketImageName, imageUrl);
            jedisPool.getResource().srem("storage", imageUrl);
            return CommonResult.success();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return CommonResult.failed();
    }

    @GetMapping("/image/{filename}")
    @ApiOperation(value = "文件下载")
    @ApiImplicitParam(name = "filename", value = "文件名称", required = true, dataType = "string", paramType = "query")
    public CommonResult downloadFile(@PathVariable(value = "filename", required = true) String filename, HttpServletResponse httpResponse) {
        try {
            InputStream object = minIoUtil.getObject(bucketImageName, "/" + serviceName + "/" + filename);
            byte buf[] = new byte[1024];
            int length = 0;
            httpResponse.reset();
            httpResponse.setHeader("Content-Disposition", "attachment;filename=" +
                    URLEncoder.encode(filename, "UTF-8"));
            httpResponse.setContentType("application/octet-stream");
            httpResponse.setCharacterEncoding("utf-8");
            OutputStream outputStream = httpResponse.getOutputStream();

            while ((length = object.read(buf)) > 0) {
                outputStream.write(buf, 0, length);
            }

            outputStream.close();

            return CommonResult.success();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return CommonResult.failed("文件下载失败");
    }
}
