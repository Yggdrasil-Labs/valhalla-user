package com.yggdrasil.labs.app.api.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
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
    @Mapping(target = "id", source = "id", qualifiedByName = "longToString")
    ApiCO toCO(Api api);

    /**
     * Long转String
     *
     * @param id Long类型的ID
     * @return String类型的ID
     */
    @Named("longToString")
    default String longToString(Long id) {
        if (id == null) {
            return null;
        }
        return String.valueOf(id);
    }
}
