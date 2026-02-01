package com.yggdrasil.labs.infrastructure.persistence.converter;

import java.util.Collections;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.yggdrasil.labs.domain.permission.model.Permission;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.PermissionDO;

/**
 * 权限对象转换器
 *
 * @author YoungerYang-Y
 */
@Mapper(componentModel = "spring")
public interface PermissionConverter {

    PermissionConverter INSTANCE = Mappers.getMapper(PermissionConverter.class);

    /**
     * DO转Entity
     *
     * @param permissionDO 数据库对象
     * @return 领域实体
     */
    @Mapping(target = "apiIds", ignore = true)
    Permission toEntity(PermissionDO permissionDO);

    /**
     * Entity转DO
     *
     * @param permission 领域实体
     * @return 数据库对象
     */
    @Mapping(target = "deletedAt", ignore = true)
    PermissionDO toDO(Permission permission);

    /**
     * DO列表转Entity列表
     *
     * @param permissionDOList DO列表
     * @return Entity列表
     */
    default List<Permission> toEntityList(List<PermissionDO> permissionDOList) {
        if (permissionDOList == null) {
            return Collections.emptyList();
        }
        return permissionDOList.stream().map(this::toEntity).toList();
    }
}
