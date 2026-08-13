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

/** {@link PermissionApplicationServiceImpl} 单元测试（验证委派） */
@ExtendWith(MockitoExtension.class)
class PermissionApplicationServiceImplTest {

    @Mock private CreatePermissionCmdExe createPermissionCmdExe;
    @Mock private UpdatePermissionCmdExe updatePermissionCmdExe;
    @Mock private DeletePermissionCmdExe deletePermissionCmdExe;
    @Mock private AssignPermissionApiCmdExe assignPermissionApiCmdExe;
    @Mock private GetPermissionQueryExe getPermissionQueryExe;
    @Mock private PagePermissionQueryExe pagePermissionQueryExe;

    @InjectMocks private PermissionApplicationServiceImpl service;

    @Test
    void createPermission_shouldDelegate() {
        CreatePermissionCmd cmd = new CreatePermissionCmd();
        Response resp = Response.buildSuccess();
        when(createPermissionCmdExe.execute(cmd)).thenReturn(resp);
        assertSame(resp, service.createPermission(cmd));
        verify(createPermissionCmdExe).execute(cmd);
    }

    @Test
    void updatePermission_shouldDelegate() {
        UpdatePermissionCmd cmd = new UpdatePermissionCmd();
        Response resp = Response.buildSuccess();
        when(updatePermissionCmdExe.execute(cmd)).thenReturn(resp);
        assertSame(resp, service.updatePermission(cmd));
        verify(updatePermissionCmdExe).execute(cmd);
    }

    @Test
    void deletePermission_shouldDelegate() {
        DeletePermissionCmd cmd = new DeletePermissionCmd();
        Response resp = Response.buildSuccess();
        when(deletePermissionCmdExe.execute(cmd)).thenReturn(resp);
        assertSame(resp, service.deletePermission(cmd));
        verify(deletePermissionCmdExe).execute(cmd);
    }

    @Test
    void assignPermissionApi_shouldDelegate() {
        AssignPermissionApiCmd cmd = new AssignPermissionApiCmd();
        Response resp = Response.buildSuccess();
        when(assignPermissionApiCmdExe.execute(cmd)).thenReturn(resp);
        assertSame(resp, service.assignPermissionApi(cmd));
        verify(assignPermissionApiCmdExe).execute(cmd);
    }

    @Test
    void getPermission_shouldDelegate() {
        GetPermissionQuery query = new GetPermissionQuery();
        SingleResponse<PermissionCO> resp = SingleResponse.of(new PermissionCO());
        when(getPermissionQueryExe.execute(query)).thenReturn(resp);
        assertSame(resp, service.getPermission(query));
        verify(getPermissionQueryExe).execute(query);
    }

    @Test
    void pagePermission_shouldDelegate() {
        PagePermissionQuery query = new PagePermissionQuery();
        PageResponse<PermissionCO> resp = PageResponse.of(List.of(), 0, 10, 0);
        when(pagePermissionQueryExe.execute(query)).thenReturn(resp);
        assertSame(resp, service.pagePermission(query));
        verify(pagePermissionQueryExe).execute(query);
    }
}
