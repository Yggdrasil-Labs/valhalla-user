package com.yggdrasil.labs.infrastructure.persistence.converter;

import java.util.Collections;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.yggdrasil.labs.domain.user.model.User;
import com.yggdrasil.labs.domain.user.model.UserStatus;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.UserDO;

/**
 * 用户对象转换器
 *
 * @author YoungerYang-Y
 */
@Mapper(componentModel = "spring")
public interface UserConverter {

    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    /**
     * DO转Entity
     *
     * @param userDO 数据库对象
     * @return 领域实体
     */
    @Mapping(target = "roleIds", ignore = true)
    @Mapping(target = "status", expression = "java(convertStatus(userDO.getStatus()))")
    User toEntity(UserDO userDO);

    /**
     * Entity转DO
     *
     * @param user 领域实体
     * @return 数据库对象
     */
    @Mapping(
            target = "status",
            expression = "java(user.getStatus() != null ? user.getStatus().getCode() : null)")
    @Mapping(target = "deletedAt", ignore = true)
    UserDO toDO(User user);

    /**
     * DO列表转Entity列表
     *
     * @param userDOList DO列表
     * @return Entity列表
     */
    default List<User> toEntityList(List<UserDO> userDOList) {
        if (userDOList == null) {
            return Collections.emptyList();
        }
        return userDOList.stream().map(this::toEntity).toList();
    }

    /**
     * 转换状态代码为枚举
     *
     * @param status 状态代码
     * @return 用户状态枚举
     */
    default UserStatus convertStatus(Integer status) {
        if (status == null) {
            return null;
        }
        return UserStatus.fromCode(status);
    }
}
