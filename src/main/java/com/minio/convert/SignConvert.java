package com.minio.convert;

import com.minio.dto.SignDTO;
import com.minio.entity.Sign;
import com.minio.vo.SignVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author yuelimin
 * @version 1.0.0
 * @since 1.8
 */
@Mapper
public interface SignConvert {
    SignConvert INSTANCE = Mappers.getMapper(SignConvert.class);

    Sign dto2entity(SignDTO signDTO);

    SignDTO entity2dto(Sign sign);

    List<SignDTO> entityList2dtoList(List<Sign> signList);

    SignDTO vo2dto(SignVO signVO);
}
