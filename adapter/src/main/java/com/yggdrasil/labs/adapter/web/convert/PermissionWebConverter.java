package com.yggdrasil.labs.adapter.web.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.yggdrasil.labs.adapter.web.request.AssignPermissionApiRequest;
import com.yggdrasil.labs.adapter.web.request.CreatePermissionRequest;
import com.yggdrasil.labs.adapter.web.request.PagePermissionRequest;
import com.yggdrasil.labs.adapter.web.request.UpdatePermissionRequest;
import com.yggdrasil.labs.client.dto.permission.cmd.AssignPermissionApiCmd;
import com.yggdrasil.labs.client.dto.permission.cmd.CreatePermissionCmd;
import com.yggdrasil.labs.client.dto.permission.cmd.DeletePermissionCmd;
import com.yggdrasil.labs.client.dto.permission.cmd.UpdatePermissionCmd;
import com.yggdrasil.labs.client.dto.permission.query.GetPermissionQuery;
import com.yggdrasil.labs.client.dto.permission.query.PagePermissionQuery;

/**
 * 权限Web转换器
 *
 * @author YoungerYang-Y
 */
@Mapper(componentModel = "spring")
public interface PermissionWebConverter {

    PermissionWebConverter INSTANCE = Mappers.getMapper(PermissionWebConverter.class);

    /**
     * Request转CreatePermissionCmd
     *
     * @param request 创建权限请求
     * @return 创建权限命令
     */
    CreatePermissionCmd toCreatePermissionCmd(CreatePermissionRequest request);

    /**
     * Request转UpdatePermissionCmd
     *
     * @param request 更新权限请求
     * @return 更新权限命令
     */
    UpdatePermissionCmd toUpdatePermissionCmd(UpdatePermissionRequest request);

    /**
     * Request转AssignPermissionApiCmd
     *
     * @param request 分配权限API请求
     * @return 分配权限API命令
     */
    AssignPermissionApiCmd toAssignPermissionApiCmd(AssignPermissionApiRequest request);

    /**
     * Request转PagePermissionQuery
     *
     * @param request 分页查询权限请求
     * @return 分页查询权限查询
     */
    PagePermissionQuery toPagePermissionQuery(PagePermissionRequest request);

    /**
     * ID转GetPermissionQuery
     *
     * @param id 权限ID
     * @return 获取权限查询
     */
    default GetPermissionQuery toGetPermissionQuery(Long id) {
        GetPermissionQuery query = new GetPermissionQuery();
        query.setId(id);
        return query;
    }

    /**
     * ID转DeletePermissionCmd
     *
     * @param id 权限ID
     * @return 删除权限命令
     */
    default DeletePermissionCmd toDeletePermissionCmd(Long id) {
        DeletePermissionCmd cmd = new DeletePermissionCmd();
        cmd.setId(id);
        return cmd;
    }
}
