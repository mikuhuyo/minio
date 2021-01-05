package com.minio.domain.exception;

import com.minio.domain.domain.IErrorCode;

/**
 * @author yuelimin
 * 异常断言
 * @since 1.8
 */
public class Asserts {
    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }
}
