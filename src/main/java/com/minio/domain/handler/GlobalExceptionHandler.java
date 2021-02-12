package com.minio.domain.handler;

import com.minio.domain.domain.CommonResult;
import com.minio.domain.domain.ResultCode;
import com.minio.domain.exception.ApiException;
import com.minio.domain.exception.Asserts;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @author yuelimin
 * @since 1.8
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return CommonResult.failed(ResultCode.VALIDATE_FAILED, e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public CommonResult<Object> handle(Exception e) {

        e.printStackTrace();

        if (e instanceof ApiException) {
            ApiException apiException = (ApiException) e;
            if (apiException.getErrorCode() != null) {
                return CommonResult.failed(apiException.getErrorCode());
            }
            return CommonResult.failed(apiException.getMessage());
        } else if (e instanceof NoHandlerFoundException) {
            Asserts.fail(ResultCode.NOT_FOUND);
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            Asserts.fail(ResultCode.HTTP_REQUEST_METHOD_NOT_SUPPORTED);
        } else if (e instanceof HttpMediaTypeNotSupportedException) {
            Asserts.fail(ResultCode.HTTP_MEDIA_TYPE_NOT_SUPPORTED);
        }

        return CommonResult.unknownError();
    }
}
