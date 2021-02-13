package com.minio.service;

import com.minio.domain.domain.CommonPage;
import com.minio.dto.SignDTO;
import com.minio.dto.TokenDTO;
import com.minio.vo.SignVO;

import java.io.UnsupportedEncodingException;

/**
 * @author yuelimin
 * @version 1.0.0
 * @since 1.8
 */
public interface SignService {
    /**
     * 获取签名信息
     *
     * @param appId 应用id
     * @return
     */
    SignDTO getSignByAppId(String appId);

    /**
     * 根据状态分页查询
     *
     * @param status 状态
     * @param page   页码
     * @param size   数据记录
     * @return
     */
    CommonPage querySignLimitByStatus(String status, Integer page, Integer size);

    /**
     * 获取加密签名信息
     *
     * @param appId 应用ID
     * @return
     */
    TokenDTO getSign(String appId) throws UnsupportedEncodingException;

    /**
     * 根据应用ID更新状态
     *
     * @param appId
     * @return
     */
    SignDTO updateSignStatusByAppId(String appId);

    /**
     * 删除签名
     *
     * @param appId 应用ID
     */
    void deleteSign(String appId);

    /**
     * 新增签名
     *
     * @param signVO
     * @return
     */
    SignDTO createSign(SignVO signVO);
}
