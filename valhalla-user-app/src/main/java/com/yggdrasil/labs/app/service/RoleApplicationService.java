package com.yggdrasil.labs.app.service;

import com.alibaba.cola.dto.PageResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.app.role.dto.cmd.AssignRolePermissionCmd;
import com.yggdrasil.labs.app.role.dto.cmd.CreateRoleCmd;
import com.yggdrasil.labs.app.role.dto.cmd.DeleteRoleCmd;
import com.yggdrasil.labs.app.role.dto.cmd.UpdateRoleCmd;
import com.yggdrasil.labs.app.role.dto.co.RoleCO;
import com.yggdrasil.labs.app.role.dto.query.GetRoleQuery;
import com.yggdrasil.labs.app.role.dto.query.PageRoleQuery;

/** 角色应用服务 */
public interface RoleApplicationService {

    Response createRole(CreateRoleCmd cmd);

    Response updateRole(UpdateRoleCmd cmd);

    Response deleteRole(DeleteRoleCmd cmd);

    SingleResponse<RoleCO> getRole(GetRoleQuery query);

    PageResponse<RoleCO> pageRole(PageRoleQuery query);

    Response assignRolePermission(AssignRolePermissionCmd cmd);
}
