package com.yggdrasil.labs.app.permission.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.yggdrasil.labs.client.dto.permission.co.PermissionCO;
import com.yggdrasil.labs.domain.permission.model.Permission;

/**
 * 权限对象组装器
 *
 * @author YoungerYang-Y
 */
@Mapper(componentModel = "spring")
public interface PermissionAssembler {

    PermissionAssembler INSTANCE = Mappers.getMapper(PermissionAssembler.class);

    /**
     * Entity转CO
     *
     * @param permission 领域实体
     * @return 客户对象
     */
    @Mapping(target = "id", source = "id", qualifiedByName = "longToString")
    @Mapping(target = "apiIds", ignore = true)
    PermissionCO toCO(Permission permission);

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
