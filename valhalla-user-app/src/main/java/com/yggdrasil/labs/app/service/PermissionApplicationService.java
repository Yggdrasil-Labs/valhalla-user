package com.yggdrasil.labs.app.service;

import com.alibaba.cola.dto.PageResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.app.permission.dto.cmd.AssignPermissionApiCmd;
import com.yggdrasil.labs.app.permission.dto.cmd.CreatePermissionCmd;
import com.yggdrasil.labs.app.permission.dto.cmd.DeletePermissionCmd;
import com.yggdrasil.labs.app.permission.dto.cmd.UpdatePermissionCmd;
import com.yggdrasil.labs.app.permission.dto.co.PermissionCO;
import com.yggdrasil.labs.app.permission.dto.query.GetPermissionQuery;
import com.yggdrasil.labs.app.permission.dto.query.PagePermissionQuery;

/** 权限应用服务 */
public interface PermissionApplicationService {

    Response createPermission(CreatePermissionCmd cmd);

    Response updatePermission(UpdatePermissionCmd cmd);

    Response deletePermission(DeletePermissionCmd cmd);

    SingleResponse<PermissionCO> getPermission(GetPermissionQuery query);

    PageResponse<PermissionCO> pagePermission(PagePermissionQuery query);

    Response assignPermissionApi(AssignPermissionApiCmd cmd);
}
