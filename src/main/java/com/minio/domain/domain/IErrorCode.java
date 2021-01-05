package com.minio.domain.domain;

/**
 * @author yuelimin
 * 封装API错误码
 * @since 1.8
 */
public interface IErrorCode {
    boolean getFlag();

    String getCode();

    String getMessage();
}

