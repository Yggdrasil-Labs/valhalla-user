package com.yggdrasil.labs.app.user.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.yggdrasil.labs.client.dto.user.co.UserCO;
import com.yggdrasil.labs.domain.user.model.User;
import com.yggdrasil.labs.domain.user.model.UserStatus;

/**
 * 用户对象组装器
 *
 * @author YoungerYang-Y
 */
@Mapper(componentModel = "spring")
public interface UserAssembler {

    UserAssembler INSTANCE = Mappers.getMapper(UserAssembler.class);

    /**
     * Entity转CO
     *
     * @param user 领域实体
     * @return 客户对象
     */
    @Mapping(target = "id", source = "id", qualifiedByName = "longToString")
    @Mapping(target = "status", source = "status", qualifiedByName = "toStatusCode")
    @Mapping(target = "roleIds", ignore = true)
    UserCO toCO(User user);

    /**
     * 转换状态枚举为代码
     *
     * @param status 用户状态枚举
     * @return 状态代码
     */
    @Named("toStatusCode")
    default Integer toStatusCode(UserStatus status) {
        if (status == null) {
            return null;
        }
        return status.getCode();
    }

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
