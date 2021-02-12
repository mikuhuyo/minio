package com.minio.domain.domain;

/**
 * @author yuelimin
 * 枚举一些常用状态码以及提示信息
 * @since 1.8
 */
public enum ResultCode implements IErrorCode {
    /**
     * 枚举的一些状态码以及提示信息
     */
    SUCCESS(true, "00000", "操作成功"),
    USER_SIGNATURE_ERROR(false, "A0340", "用户签名异常"),
    RSA_SIGNATURE_ERROR(false, "A0341", "RSA签名错误"),
    VALIDATE_FAILED(false, "A0400", "用户请求错误"),
    FAILED(false, "B0001", "系统执行失败"),
    UNKNOWN_ERROR(false, "B0300", "系统资源异常"),
    NOT_FOUND(false, "B0404", "对象丢失"),
    HTTP_REQUEST_METHOD_NOT_SUPPORTED(false, "B0405", "请求方法不支持"),
    HTTP_MEDIA_TYPE_NOT_SUPPORTED(false, "B0415", "不支持媒体类型"),
    REMOTE_ERROR(false, "C0001", "第三方调用错误");

    private boolean flag;
    private String code;
    private String message;

    private ResultCode(boolean flag, String code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean getFlag() {
        return flag;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

