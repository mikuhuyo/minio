package com.minio.domain.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author yuelimin
 * @since 1.8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResult<T> implements Serializable {
    private boolean flag;
    private String code;
    private String message;
    private T data;

    public CommonResult(boolean flag, String code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    /**
     * 操作成功
     *
     * @param
     */
    public static <T> CommonResult<T> success() {
        return new CommonResult<T>(ResultCode.SUCCESS.getFlag(),
                ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMessage());
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(ResultCode.SUCCESS.getFlag(),
                ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMessage(),
                data);
    }

    /**
     * 成功返回结果
     *
     * @param data    获取的数据
     * @param message 提示信息
     */
    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<T>(ResultCode.SUCCESS.getFlag(),
                ResultCode.SUCCESS.getCode(),
                message,
                data);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     */
    public static <T> CommonResult<T> failed(IErrorCode errorCode) {
        return new CommonResult<T>(errorCode.getFlag(),
                errorCode.getCode(),
                errorCode.getMessage(),
                null);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     * @param message   错误信息
     */
    public static <T> CommonResult<T> failed(IErrorCode errorCode, String message) {
        return new CommonResult<T>(errorCode.getFlag(),
                errorCode.getCode(),
                message,
                null);
    }

    /**
     * 失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> CommonResult<T> failed(String message) {
        return new CommonResult<T>(ResultCode.FAILED.getFlag(),
                ResultCode.FAILED.getCode(),
                message,
                null);
    }

    /**
     * 失败返回结果
     */
    public static <T> CommonResult<T> failed() {
        return failed(ResultCode.FAILED);
    }

    public static <T> CommonResult<T> unknownError() {
        return new CommonResult<T>(ResultCode.UNKNOWN_ERROR.getFlag(),
                ResultCode.UNKNOWN_ERROR.getCode(),
                ResultCode.UNKNOWN_ERROR.getMessage(),
                null);
    }
}

