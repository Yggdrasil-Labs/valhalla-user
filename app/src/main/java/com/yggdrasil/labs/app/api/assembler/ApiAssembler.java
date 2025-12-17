package com.yggdrasil.labs.app.api.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.yggdrasil.labs.client.dto.api.co.ApiCO;
import com.yggdrasil.labs.domain.api.model.Api;

/**
 * API对象组装器
 *
 * @author YoungerYang-Y
 */
@Mapper(componentModel = "spring")
public interface ApiAssembler {

    ApiAssembler INSTANCE = Mappers.getMapper(ApiAssembler.class);

    /**
     * Entity转CO
     *
     * @param api 领域实体
     * @return 客户对象
     */
    ApiCO toCO(Api api);
}
