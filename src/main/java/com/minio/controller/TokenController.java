package com.minio.controller;

import com.minio.domain.domain.CommonResult;
import com.minio.dto.SignDTO;
import com.minio.dto.TokenDTO;
import com.minio.service.SignService;
import com.minio.vo.SignVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

/**
 * @author yuelimin
 * @version 1.0.0
 * @since 1.8
 */
@RestController
@Api(value = "签名获取与添加", tags = "Token")
public class TokenController {
    @Autowired
    private SignService signService;

    @GetMapping("/token/status/{status}/{page}/{size}")
    @ApiOperation("根据状态获取签名分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "状态", name = "status", required = true, dataType = "string"),
            @ApiImplicitParam(value = "页码", name = "page", required = true, dataType = "integer"),
            @ApiImplicitParam(value = "记录", name = "size", required = true, dataType = "integer")
    })
    public CommonResult querySignLimitByStatus(@PathVariable("status") String status, @PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return CommonResult.success(signService.querySignLimitByStatus(status, page, size));
    }

    @GetMapping("/token/sign/{appId}")
    @ApiOperation("获取加密签名")
    @ApiImplicitParam(value = "应用ID", name = "appId", required = true, dataType = "string")
    public CommonResult<TokenDTO> getSign(@PathVariable("appId") String appId) throws UnsupportedEncodingException {
        return CommonResult.success(signService.getSign(appId));
    }

    @PutMapping("/token/sign/{appId}")
    @ApiOperation("更新签名状态")
    @ApiImplicitParam(value = "应用ID", name = "appId", required = true, dataType = "string")
    public CommonResult<SignDTO> updateSignStatusByAppIdSign(@PathVariable("appId") String appId) {
        return CommonResult.success(signService.updateSignStatusByAppId(appId));
    }

    @DeleteMapping("/token/sign/{appId}")
    @ApiOperation("根据应用ID删除签名")
    @ApiImplicitParam(value = "应用ID", name = "appId", required = true, dataType = "string")
    public CommonResult deleteSign(@PathVariable("appId") String appId) {
        signService.deleteSign(appId);
        return CommonResult.success();
    }

    @PostMapping("/token/signs")
    @ApiOperation("添加用户签名")
    @ApiImplicitParam(value = "签名信息", name = "signVO", required = true, dataType = "SignVO", paramType = "body")
    public CommonResult<SignDTO> createSign(@RequestBody @Valid SignVO signVO) {
        return CommonResult.success(signService.createSign(signVO));
    }
}
