package com.minio.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author yuelimin
 * @version 1.0.0
 * @since 1.8
 */
@Data
@ApiModel(value = "SignVO", description = "sign vo")
public class SignVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty("username")
    private String username;

    @NotBlank(message = "用户密码不能为空")
    @ApiModelProperty("password")
    private String password;
}
