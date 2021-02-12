package com.minio.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minio.convert.SignConvert;
import com.minio.domain.domain.CommonPage;
import com.minio.dto.SignDTO;
import com.minio.dto.TokenDTO;
import com.minio.entity.Sign;
import com.minio.mapper.SignMapper;
import com.minio.utils.BCryptUtil;
import com.minio.vo.SignVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @author yuelimin
 * @version 1.0.0
 * @since 1.8
 */
@Service
public class SignServiceImpl implements SignService {
    @Autowired
    private SignMapper signMapper;

    @Override
    public SignDTO getSignByAppId(String appId) {
        return SignConvert.INSTANCE.entity2dto(signMapper.selectOne(new LambdaQueryWrapper<Sign>().eq(Sign::getAppId, appId)));
    }

    @Override
    public CommonPage querySignLimitByStatus(String status, Integer page, Integer size) {
        IPage<Sign> signIPage = signMapper.selectPage(new Page<Sign>(page, size), new LambdaQueryWrapper<Sign>().eq(Sign::getStatus, status));
        return new CommonPage<>(signIPage.getTotal(), SignConvert.INSTANCE.entityList2dtoList(signIPage.getRecords()));
    }

    @Override
    public TokenDTO getSign(String appId) {
        Sign sign = signMapper.selectOne(new LambdaQueryWrapper<Sign>().eq(Sign::getAppId, appId));
        String access = BCryptUtil.hashpw(sign.getUsername(), sign.getSalt());
        String secret = BCryptUtil.hashpw(sign.getSalt(), sign.getSalt());

        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setAppId(appId);
        tokenDTO.setAccessKey(access);
        tokenDTO.setSecretKey(secret);
        return tokenDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SignDTO updateSignStatusByAppId(String appId) {
        Sign sign = signMapper.selectOne(new LambdaQueryWrapper<Sign>().eq(Sign::getAppId, appId));
        String status = "0".equals(sign.getStatus()) ? "1" : "0";

        Sign newSign = new Sign();
        newSign.setId(sign.getId());
        newSign.setStatus(status);
        signMapper.updateById(newSign);

        Sign modifySign = new Sign();
        modifySign.setStatus("0".equals(status) ? "1" : "0");
        signMapper.update(modifySign, new LambdaQueryWrapper<Sign>().ne(Sign::getAppId, appId));

        sign.setStatus(status);
        return SignConvert.INSTANCE.entity2dto(sign);
    }

    @Override
    public void deleteSign(String appId) {
        signMapper.delete(new LambdaQueryWrapper<Sign>().eq(Sign::getAppId, appId));
    }

    @Override
    public SignDTO createSign(SignVO signVO) {
        SignDTO signDTO = SignConvert.INSTANCE.vo2dto(signVO);
        signDTO.setStatus("0");
        signDTO.setAppId(UUID.randomUUID().toString().replace("-", ""));
        String salt = BCryptUtil.gensalt();
        String password = BCryptUtil.hashpw(signVO.getPassword(), salt);
        signDTO.setPassword(password);
        signDTO.setSalt(salt);

        Sign sign = SignConvert.INSTANCE.dto2entity(signDTO);
        signMapper.insert(sign);
        return SignConvert.INSTANCE.entity2dto(sign);
    }
}
