package com.yggdrasil.labs.infrastructure.persistence.converter;

import java.util.Collections;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.yggdrasil.labs.domain.role.model.Role;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.RoleDO;

/**
 * 角色对象转换器
 *
 * @author YoungerYang-Y
 */
@Mapper(componentModel = "spring")
public interface RoleConverter {

    RoleConverter INSTANCE = Mappers.getMapper(RoleConverter.class);

    /**
     * DO转Entity
     *
     * @param roleDO 数据库对象
     * @return 领域实体
     */
    @Mapping(target = "permissionIds", ignore = true)
    Role toEntity(RoleDO roleDO);

    /**
     * Entity转DO
     *
     * @param role 领域实体
     * @return 数据库对象
     */
    @Mapping(target = "deletedAt", ignore = true)
    RoleDO toDO(Role role);

    /**
     * DO列表转Entity列表
     *
     * @param roleDOList DO列表
     * @return Entity列表
     */
    default List<Role> toEntityList(List<RoleDO> roleDOList) {
        if (roleDOList == null) {
            return Collections.emptyList();
        }
        return roleDOList.stream().map(this::toEntity).toList();
    }
}
