package com.minio.intercept;

import com.minio.domain.domain.ResultCode;
import com.minio.domain.exception.Asserts;
import com.minio.dto.SignDTO;
import com.minio.service.SignService;
import com.minio.utils.BCryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

/**
 * @author yuelimin
 * @version 1.0.0
 * @since 1.8
 */
@Component
public class RequestSignatureIntercept extends HandlerInterceptorAdapter {
    @Autowired
    private SignService signService;

    private final Base64.Decoder decoder = Base64.getDecoder();
    private final Base64.Encoder encoder = Base64.getEncoder();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String appId = request.getHeader("AppId");
        String accessKey = request.getHeader("AccessKey");
        String secretKey = request.getHeader("SecretKey");

        accessKey = new String(decoder.decode(accessKey), "UTF-8");
        secretKey = new String(decoder.decode(secretKey), "UTF-8");

        if (accessKey == null || secretKey == null || appId == null) {
            Asserts.fail(ResultCode.USER_SIGNATURE_ERROR);
        }

        SignDTO sign = signService.getSignByAppId(appId);

        boolean access = BCryptUtil.checkpw(sign.getUsername(), accessKey);
        boolean secret = BCryptUtil.checkpw(sign.getSalt(), secretKey);

        if (!access || !secret) {
            Asserts.fail(ResultCode.RSA_SIGNATURE_ERROR);
        }

        return true;
    }
}
