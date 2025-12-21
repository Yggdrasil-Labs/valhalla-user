package com.yggdrasil.labs.adapter.web.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.yggdrasil.labs.adapter.web.request.AssignRolePermissionRequest;
import com.yggdrasil.labs.adapter.web.request.CreateRoleRequest;
import com.yggdrasil.labs.adapter.web.request.PageRoleRequest;
import com.yggdrasil.labs.adapter.web.request.UpdateRoleRequest;
import com.yggdrasil.labs.client.dto.role.cmd.AssignRolePermissionCmd;
import com.yggdrasil.labs.client.dto.role.cmd.CreateRoleCmd;
import com.yggdrasil.labs.client.dto.role.cmd.DeleteRoleCmd;
import com.yggdrasil.labs.client.dto.role.cmd.UpdateRoleCmd;
import com.yggdrasil.labs.client.dto.role.query.GetRoleQuery;
import com.yggdrasil.labs.client.dto.role.query.PageRoleQuery;

/**
 * 角色Web转换器
 *
 * @author YoungerYang-Y
 */
@Mapper(componentModel = "spring")
public interface RoleWebConverter {

    RoleWebConverter INSTANCE = Mappers.getMapper(RoleWebConverter.class);

    /**
     * Request转CreateRoleCmd
     *
     * @param request 创建角色请求
     * @return 创建角色命令
     */
    CreateRoleCmd toCreateRoleCmd(CreateRoleRequest request);

    /**
     * Request转UpdateRoleCmd
     *
     * @param request 更新角色请求
     * @return 更新角色命令
     */
    UpdateRoleCmd toUpdateRoleCmd(UpdateRoleRequest request);

    /**
     * Request转AssignRolePermissionCmd
     *
     * @param roleId 角色ID（从URL路径参数获取）
     * @param request 分配角色权限请求
     * @return 分配角色权限命令
     */
    default AssignRolePermissionCmd toAssignRolePermissionCmd(Long roleId, AssignRolePermissionRequest request) {
        AssignRolePermissionCmd cmd = new AssignRolePermissionCmd();
        cmd.setRoleId(roleId);
        cmd.setPermissionIds(request.getPermissionIds());
        return cmd;
    }

    /**
     * Request转PageRoleQuery
     *
     * @param request 分页查询角色请求
     * @return 分页查询角色查询
     */
    PageRoleQuery toPageRoleQuery(PageRoleRequest request);

    /**
     * ID转GetRoleQuery
     *
     * @param id 角色ID
     * @return 获取角色查询
     */
    default GetRoleQuery toGetRoleQuery(Long id) {
        GetRoleQuery query = new GetRoleQuery();
        query.setId(id);
        return query;
    }

    /**
     * ID转DeleteRoleCmd
     *
     * @param id 角色ID
     * @return 删除角色命令
     */
    default DeleteRoleCmd toDeleteRoleCmd(Long id) {
        DeleteRoleCmd cmd = new DeleteRoleCmd();
        cmd.setId(id);
        return cmd;
    }
}
