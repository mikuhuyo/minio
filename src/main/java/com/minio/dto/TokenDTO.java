package com.minio.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yuelimin
 * @version 1.0.0
 * @since 1.8
 */
@Data
@ApiModel(value = "TokenDTO", description = "token dto")
public class TokenDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("应用ID")
    private String appId;

    @ApiModelProperty("进入秘钥")
    private String accessKey;

    @ApiModelProperty("私钥")
    private String secretKey;
}
