package com.yggdrasil.labs.client.api;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.client.dto.role.cmd.AssignRolePermissionCmd;
import com.yggdrasil.labs.client.dto.role.cmd.CreateRoleCmd;
import com.yggdrasil.labs.client.dto.role.cmd.DeleteRoleCmd;
import com.yggdrasil.labs.client.dto.role.cmd.UpdateRoleCmd;
import com.yggdrasil.labs.client.dto.role.co.RoleCO;
import com.yggdrasil.labs.client.dto.role.query.GetRoleQuery;
import com.yggdrasil.labs.client.dto.role.query.PageRoleQuery;

/**
 * 角色客户端接口
 *
 * @author YoungerYang-Y
 */
public interface RoleClient {

    /**
     * 创建角色
     *
     * @param cmd 创建角色命令
     * @return 响应结果
     */
    Response createRole(CreateRoleCmd cmd);

    /**
     * 更新角色
     *
     * @param cmd 更新角色命令
     * @return 响应结果
     */
    Response updateRole(UpdateRoleCmd cmd);

    /**
     * 删除角色
     *
     * @param cmd 删除角色命令
     * @return 响应结果
     */
    Response deleteRole(DeleteRoleCmd cmd);

    /**
     * 获取角色详情
     *
     * @param query 查询条件
     * @return 角色信息
     */
    SingleResponse<RoleCO> getRole(GetRoleQuery query);

    /**
     * 分页查询角色列表
     *
     * @param query 查询条件
     * @return 角色列表
     */
    MultiResponse<RoleCO> pageRole(PageRoleQuery query);

    /**
     * 分配角色权限
     *
     * @param cmd 分配权限命令
     * @return 响应结果
     */
    Response assignRolePermission(AssignRolePermissionCmd cmd);
}
