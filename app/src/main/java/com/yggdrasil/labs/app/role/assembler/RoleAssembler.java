package com.yggdrasil.labs.app.role.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.yggdrasil.labs.client.dto.role.co.RoleCO;
import com.yggdrasil.labs.domain.role.model.Role;

/**
 * 角色对象组装器
 *
 * @author YoungerYang-Y
 */
@Mapper(componentModel = "spring")
public interface RoleAssembler {

    RoleAssembler INSTANCE = Mappers.getMapper(RoleAssembler.class);

    /**
     * Entity转CO
     *
     * @param role 领域实体
     * @return 客户对象
     */
    @Mapping(target = "id", source = "id", qualifiedByName = "longToString")
    @Mapping(target = "permissionIds", ignore = true)
    RoleCO toCO(Role role);

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
