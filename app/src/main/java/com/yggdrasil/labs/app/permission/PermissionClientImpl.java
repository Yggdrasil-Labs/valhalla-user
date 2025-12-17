package com.yggdrasil.labs.app.permission;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.app.permission.executor.AssignPermissionApiCmdExe;
import com.yggdrasil.labs.app.permission.executor.CreatePermissionCmdExe;
import com.yggdrasil.labs.app.permission.executor.DeletePermissionCmdExe;
import com.yggdrasil.labs.app.permission.executor.UpdatePermissionCmdExe;
import com.yggdrasil.labs.app.permission.query.GetPermissionQueryExe;
import com.yggdrasil.labs.app.permission.query.PagePermissionQueryExe;
import com.yggdrasil.labs.client.api.PermissionClient;
import com.yggdrasil.labs.client.dto.permission.cmd.AssignPermissionApiCmd;
import com.yggdrasil.labs.client.dto.permission.cmd.CreatePermissionCmd;
import com.yggdrasil.labs.client.dto.permission.cmd.DeletePermissionCmd;
import com.yggdrasil.labs.client.dto.permission.cmd.UpdatePermissionCmd;
import com.yggdrasil.labs.client.dto.permission.co.PermissionCO;
import com.yggdrasil.labs.client.dto.permission.query.GetPermissionQuery;
import com.yggdrasil.labs.client.dto.permission.query.PagePermissionQuery;

/**
 * 权限客户端实现
 *
 * @author YoungerYang-Y
 */
@Service
public class PermissionClientImpl implements PermissionClient {

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
    public MultiResponse<PermissionCO> pagePermission(PagePermissionQuery query) {
        return pagePermissionQueryExe.execute(query);
    }

    @Override
    public Response assignPermissionApi(AssignPermissionApiCmd cmd) {
        return assignPermissionApiCmdExe.execute(cmd);
    }
}
