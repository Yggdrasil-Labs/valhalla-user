package com.yggdrasil.labs.client.api;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.client.dto.user.cmd.AssignUserRoleCmd;
import com.yggdrasil.labs.client.dto.user.cmd.CreateUserCmd;
import com.yggdrasil.labs.client.dto.user.cmd.DeleteUserCmd;
import com.yggdrasil.labs.client.dto.user.cmd.UpdateUserCmd;
import com.yggdrasil.labs.client.dto.user.co.UserCO;
import com.yggdrasil.labs.client.dto.user.query.GetUserQuery;
import com.yggdrasil.labs.client.dto.user.query.PageUserQuery;

/**
 * 用户客户端接口
 *
 * @author YoungerYang-Y
 */
public interface UserClient {

    /**
     * 创建用户
     *
     * @param cmd 创建用户命令
     * @return 响应结果
     */
    Response createUser(CreateUserCmd cmd);

    /**
     * 更新用户
     *
     * @param cmd 更新用户命令
     * @return 响应结果
     */
    Response updateUser(UpdateUserCmd cmd);

    /**
     * 删除用户
     *
     * @param cmd 删除用户命令
     * @return 响应结果
     */
    Response deleteUser(DeleteUserCmd cmd);

    /**
     * 获取用户详情
     *
     * @param query 查询条件
     * @return 用户信息
     */
    SingleResponse<UserCO> getUser(GetUserQuery query);

    /**
     * 分页查询用户列表
     *
     * @param query 查询条件
     * @return 用户列表
     */
    MultiResponse<UserCO> pageUser(PageUserQuery query);

    /**
     * 分配用户角色
     *
     * @param cmd 分配角色命令
     * @return 响应结果
     */
    Response assignUserRole(AssignUserRoleCmd cmd);
}
