package com.yggdrasil.labs.app.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
import com.yggdrasil.labs.app.role.executor.AssignRolePermissionCmdExe;
import com.yggdrasil.labs.app.role.executor.CreateRoleCmdExe;
import com.yggdrasil.labs.app.role.executor.DeleteRoleCmdExe;
import com.yggdrasil.labs.app.role.executor.UpdateRoleCmdExe;
import com.yggdrasil.labs.app.role.query.GetRoleQueryExe;
import com.yggdrasil.labs.app.role.query.PageRoleQueryExe;

/** {@link RoleApplicationServiceImpl} 单元测试（验证委派） */
@ExtendWith(MockitoExtension.class)
class RoleApplicationServiceImplTest {

    @Mock private CreateRoleCmdExe createRoleCmdExe;
    @Mock private UpdateRoleCmdExe updateRoleCmdExe;
    @Mock private DeleteRoleCmdExe deleteRoleCmdExe;
    @Mock private AssignRolePermissionCmdExe assignRolePermissionCmdExe;
    @Mock private GetRoleQueryExe getRoleQueryExe;
    @Mock private PageRoleQueryExe pageRoleQueryExe;

    @InjectMocks private RoleApplicationServiceImpl service;

    @Test
    void createRole_shouldDelegate() {
        CreateRoleCmd cmd = new CreateRoleCmd();
        Response resp = Response.buildSuccess();
        when(createRoleCmdExe.execute(cmd)).thenReturn(resp);
        assertSame(resp, service.createRole(cmd));
        verify(createRoleCmdExe).execute(cmd);
    }

    @Test
    void updateRole_shouldDelegate() {
        UpdateRoleCmd cmd = new UpdateRoleCmd();
        Response resp = Response.buildSuccess();
        when(updateRoleCmdExe.execute(cmd)).thenReturn(resp);
        assertSame(resp, service.updateRole(cmd));
        verify(updateRoleCmdExe).execute(cmd);
    }

    @Test
    void deleteRole_shouldDelegate() {
        DeleteRoleCmd cmd = new DeleteRoleCmd();
        Response resp = Response.buildSuccess();
        when(deleteRoleCmdExe.execute(cmd)).thenReturn(resp);
        assertSame(resp, service.deleteRole(cmd));
        verify(deleteRoleCmdExe).execute(cmd);
    }

    @Test
    void assignRolePermission_shouldDelegate() {
        AssignRolePermissionCmd cmd = new AssignRolePermissionCmd();
        Response resp = Response.buildSuccess();
        when(assignRolePermissionCmdExe.execute(cmd)).thenReturn(resp);
        assertSame(resp, service.assignRolePermission(cmd));
        verify(assignRolePermissionCmdExe).execute(cmd);
    }

    @Test
    void getRole_shouldDelegate() {
        GetRoleQuery query = new GetRoleQuery();
        SingleResponse<RoleCO> resp = SingleResponse.of(new RoleCO());
        when(getRoleQueryExe.execute(query)).thenReturn(resp);
        assertSame(resp, service.getRole(query));
        verify(getRoleQueryExe).execute(query);
    }

    @Test
    void pageRole_shouldDelegate() {
        PageRoleQuery query = new PageRoleQuery();
        PageResponse<RoleCO> resp = PageResponse.of(List.of(), 0, 10, 0);
        when(pageRoleQueryExe.execute(query)).thenReturn(resp);
        assertSame(resp, service.pageRole(query));
        verify(pageRoleQueryExe).execute(query);
    }
}
