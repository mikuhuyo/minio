package com.minio.intercept;

import com.minio.domain.domain.ResultCode;
import com.minio.domain.exception.Asserts;
import com.minio.utils.MD5Util;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yuelimin
 * @version 1.0.0
 * @since 1.8
 */
public class RequestSignatureIntercept extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessKey = request.getHeader("AccessKey");
        String secretKey = request.getHeader("SecretKey");

        if (accessKey == null || secretKey == null) {
            Asserts.fail(ResultCode.USER_SIGNATURE_ERROR);
        }

        boolean access = MD5Util.passwordIsTrue("mikuhuyo", accessKey);
        boolean secret = MD5Util.passwordIsTrue("mikuhuyo", secretKey);

        if (!access || !secret) {
            Asserts.fail(ResultCode.RSA_SIGNATURE_ERROR);
        }

        return true;
    }
}
