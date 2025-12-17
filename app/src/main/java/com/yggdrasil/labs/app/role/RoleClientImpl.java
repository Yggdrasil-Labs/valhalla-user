package com.yggdrasil.labs.app.role;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.app.role.executor.AssignRolePermissionCmdExe;
import com.yggdrasil.labs.app.role.executor.CreateRoleCmdExe;
import com.yggdrasil.labs.app.role.executor.DeleteRoleCmdExe;
import com.yggdrasil.labs.app.role.executor.UpdateRoleCmdExe;
import com.yggdrasil.labs.app.role.query.GetRoleQueryExe;
import com.yggdrasil.labs.app.role.query.PageRoleQueryExe;
import com.yggdrasil.labs.client.api.RoleClient;
import com.yggdrasil.labs.client.dto.role.cmd.AssignRolePermissionCmd;
import com.yggdrasil.labs.client.dto.role.cmd.CreateRoleCmd;
import com.yggdrasil.labs.client.dto.role.cmd.DeleteRoleCmd;
import com.yggdrasil.labs.client.dto.role.cmd.UpdateRoleCmd;
import com.yggdrasil.labs.client.dto.role.co.RoleCO;
import com.yggdrasil.labs.client.dto.role.query.GetRoleQuery;
import com.yggdrasil.labs.client.dto.role.query.PageRoleQuery;

/**
 * 角色客户端实现
 *
 * @author YoungerYang-Y
 */
@Service
public class RoleClientImpl implements RoleClient {

    @Resource private CreateRoleCmdExe createRoleCmdExe;

    @Resource private UpdateRoleCmdExe updateRoleCmdExe;

    @Resource private DeleteRoleCmdExe deleteRoleCmdExe;

    @Resource private AssignRolePermissionCmdExe assignRolePermissionCmdExe;

    @Resource private GetRoleQueryExe getRoleQueryExe;

    @Resource private PageRoleQueryExe pageRoleQueryExe;

    @Override
    public Response createRole(CreateRoleCmd cmd) {
        return createRoleCmdExe.execute(cmd);
    }

    @Override
    public Response updateRole(UpdateRoleCmd cmd) {
        return updateRoleCmdExe.execute(cmd);
    }

    @Override
    public Response deleteRole(DeleteRoleCmd cmd) {
        return deleteRoleCmdExe.execute(cmd);
    }

    @Override
    public SingleResponse<RoleCO> getRole(GetRoleQuery query) {
        return getRoleQueryExe.execute(query);
    }

    @Override
    public MultiResponse<RoleCO> pageRole(PageRoleQuery query) {
        return pageRoleQueryExe.execute(query);
    }

    @Override
    public Response assignRolePermission(AssignRolePermissionCmd cmd) {
        return assignRolePermissionCmdExe.execute(cmd);
    }
}
