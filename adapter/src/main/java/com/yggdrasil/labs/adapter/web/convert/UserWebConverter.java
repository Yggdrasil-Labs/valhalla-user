package com.yggdrasil.labs.adapter.web.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.yggdrasil.labs.adapter.web.request.AssignUserRoleRequest;
import com.yggdrasil.labs.adapter.web.request.CreateUserRequest;
import com.yggdrasil.labs.adapter.web.request.PageUserRequest;
import com.yggdrasil.labs.adapter.web.request.UpdateUserRequest;
import com.yggdrasil.labs.client.dto.user.cmd.AssignUserRoleCmd;
import com.yggdrasil.labs.client.dto.user.cmd.CreateUserCmd;
import com.yggdrasil.labs.client.dto.user.cmd.DeleteUserCmd;
import com.yggdrasil.labs.client.dto.user.cmd.UpdateUserCmd;
import com.yggdrasil.labs.client.dto.user.query.GetUserQuery;
import com.yggdrasil.labs.client.dto.user.query.PageUserQuery;

/**
 * 用户Web转换器
 *
 * @author YoungerYang-Y
 */
@Mapper(componentModel = "spring")
public interface UserWebConverter {

    UserWebConverter INSTANCE = Mappers.getMapper(UserWebConverter.class);

    /**
     * Request转CreateUserCmd
     *
     * @param request 创建用户请求
     * @return 创建用户命令
     */
    CreateUserCmd toCreateUserCmd(CreateUserRequest request);

    /**
     * Request转UpdateUserCmd
     *
     * @param request 更新用户请求
     * @return 更新用户命令
     */
    UpdateUserCmd toUpdateUserCmd(UpdateUserRequest request);

    /**
     * Request转AssignUserRoleCmd
     *
     * @param request 分配用户角色请求
     * @return 分配用户角色命令
     */
    AssignUserRoleCmd toAssignUserRoleCmd(AssignUserRoleRequest request);

    /**
     * Request转PageUserQuery
     *
     * @param request 分页查询用户请求
     * @return 分页查询用户查询
     */
    PageUserQuery toPageUserQuery(PageUserRequest request);

    /**
     * ID转GetUserQuery
     *
     * @param id 用户ID
     * @return 获取用户查询
     */
    default GetUserQuery toGetUserQuery(Long id) {
        GetUserQuery query = new GetUserQuery();
        query.setId(id);
        return query;
    }

    /**
     * ID转DeleteUserCmd
     *
     * @param id 用户ID
     * @return 删除用户命令
     */
    default DeleteUserCmd toDeleteUserCmd(Long id) {
        DeleteUserCmd cmd = new DeleteUserCmd();
        cmd.setId(id);
        return cmd;
    }
}
