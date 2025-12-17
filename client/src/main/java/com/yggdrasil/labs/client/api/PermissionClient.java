package com.yggdrasil.labs.client.api;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.client.dto.permission.cmd.AssignPermissionApiCmd;
import com.yggdrasil.labs.client.dto.permission.cmd.CreatePermissionCmd;
import com.yggdrasil.labs.client.dto.permission.cmd.DeletePermissionCmd;
import com.yggdrasil.labs.client.dto.permission.cmd.UpdatePermissionCmd;
import com.yggdrasil.labs.client.dto.permission.co.PermissionCO;
import com.yggdrasil.labs.client.dto.permission.query.GetPermissionQuery;
import com.yggdrasil.labs.client.dto.permission.query.PagePermissionQuery;

/**
 * 权限客户端接口
 *
 * @author YoungerYang-Y
 */
public interface PermissionClient {

    /**
     * 创建权限
     *
     * @param cmd 创建权限命令
     * @return 响应结果
     */
    Response createPermission(CreatePermissionCmd cmd);

    /**
     * 更新权限
     *
     * @param cmd 更新权限命令
     * @return 响应结果
     */
    Response updatePermission(UpdatePermissionCmd cmd);

    /**
     * 删除权限
     *
     * @param cmd 删除权限命令
     * @return 响应结果
     */
    Response deletePermission(DeletePermissionCmd cmd);

    /**
     * 获取权限详情
     *
     * @param query 查询条件
     * @return 权限信息
     */
    SingleResponse<PermissionCO> getPermission(GetPermissionQuery query);

    /**
     * 分页查询权限列表
     *
     * @param query 查询条件
     * @return 权限列表
     */
    MultiResponse<PermissionCO> pagePermission(PagePermissionQuery query);

    /**
     * 分配权限API
     *
     * @param cmd 分配API命令
     * @return 响应结果
     */
    Response assignPermissionApi(AssignPermissionApiCmd cmd);
}
