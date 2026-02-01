package com.yggdrasil.labs.app.service.impl;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Service;

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
import com.yggdrasil.labs.app.permission.executor.AssignPermissionApiCmdExe;
import com.yggdrasil.labs.app.permission.executor.CreatePermissionCmdExe;
import com.yggdrasil.labs.app.permission.executor.DeletePermissionCmdExe;
import com.yggdrasil.labs.app.permission.executor.UpdatePermissionCmdExe;
import com.yggdrasil.labs.app.permission.query.GetPermissionQueryExe;
import com.yggdrasil.labs.app.permission.query.PagePermissionQueryExe;
import com.yggdrasil.labs.app.service.PermissionApplicationService;

/** 权限应用服务实现 */
@Service
public class PermissionApplicationServiceImpl implements PermissionApplicationService {

    @Resource private CreatePermissionCmdExe createPermissionCmdExe;

    @Resource private UpdatePermissionCmdExe updatePermissionCmdExe;

    @Resource private DeletePermissionCmdExe deletePermissionCmdExe;

    @Resource private AssignPermissionApiCmdExe assignPermissionApiCmdExe;

    @Resource private GetPermissionQueryExe getPermissionQueryExe;

    @Resource private PagePermissionQueryExe pagePermissionQueryExe;

    @Override
    public Response createPermission(CreatePermissionCmd cmd) {
        return createPermissionCmdExe.execute(cmd);
    }

    @Override
    public Response updatePermission(UpdatePermissionCmd cmd) {
        return updatePermissionCmdExe.execute(cmd);
    }

    @Override
    public Response deletePermission(DeletePermissionCmd cmd) {
        return deletePermissionCmdExe.execute(cmd);
    }

    @Override
    public SingleResponse<PermissionCO> getPermission(GetPermissionQuery query) {
        return getPermissionQueryExe.execute(query);
    }

    @Override
    public PageResponse<PermissionCO> pagePermission(PagePermissionQuery query) {
        return pagePermissionQueryExe.execute(query);
    }

    @Override
    public Response assignPermissionApi(AssignPermissionApiCmd cmd) {
        return assignPermissionApiCmdExe.execute(cmd);
    }
}
