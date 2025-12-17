package com.yggdrasil.labs.infrastructure.persistence.converter;

import java.util.Collections;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.yggdrasil.labs.domain.api.model.Api;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.ApiDO;

/**
 * API对象转换器
 *
 * @author YoungerYang-Y
 */
@Mapper(componentModel = "spring")
public interface ApiConverter {

    ApiConverter INSTANCE = Mappers.getMapper(ApiConverter.class);

    /**
     * DO转Entity
     *
     * @param apiDO 数据库对象
     * @return 领域实体
     */
    Api toEntity(ApiDO apiDO);

    /**
     * Entity转DO
     *
     * @param api 领域实体
     * @return 数据库对象
     */
    @Mapping(target = "deletedAt", ignore = true)
    ApiDO toDO(Api api);

    /**
     * DO列表转Entity列表
     *
     * @param apiDOList DO列表
     * @return Entity列表
     */
    default List<Api> toEntityList(List<ApiDO> apiDOList) {
        if (apiDOList == null) {
            return Collections.emptyList();
        }
        return apiDOList.stream().map(this::toEntity).toList();
    }
}
