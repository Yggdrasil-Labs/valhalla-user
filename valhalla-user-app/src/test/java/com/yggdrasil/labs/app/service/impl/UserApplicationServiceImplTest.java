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
import com.yggdrasil.labs.app.user.dto.cmd.AssignUserRoleCmd;
import com.yggdrasil.labs.app.user.dto.cmd.CreateUserCmd;
import com.yggdrasil.labs.app.user.dto.cmd.DeleteUserCmd;
import com.yggdrasil.labs.app.user.dto.cmd.UpdateUserCmd;
import com.yggdrasil.labs.app.user.dto.co.UserCO;
import com.yggdrasil.labs.app.user.dto.query.GetUserQuery;
import com.yggdrasil.labs.app.user.dto.query.PageUserQuery;
import com.yggdrasil.labs.app.user.executor.AssignUserRoleCmdExe;
import com.yggdrasil.labs.app.user.executor.CreateUserCmdExe;
import com.yggdrasil.labs.app.user.executor.DeleteUserCmdExe;
import com.yggdrasil.labs.app.user.executor.UpdateUserCmdExe;
import com.yggdrasil.labs.app.user.query.GetUserQueryExe;
import com.yggdrasil.labs.app.user.query.PageUserQueryExe;

/** {@link UserApplicationServiceImpl} 单元测试（验证委派） */
@ExtendWith(MockitoExtension.class)
class UserApplicationServiceImplTest {

    @Mock private CreateUserCmdExe createUserCmdExe;
    @Mock private UpdateUserCmdExe updateUserCmdExe;
    @Mock private DeleteUserCmdExe deleteUserCmdExe;
    @Mock private AssignUserRoleCmdExe assignUserRoleCmdExe;
    @Mock private GetUserQueryExe getUserQueryExe;
    @Mock private PageUserQueryExe pageUserQueryExe;

    @InjectMocks private UserApplicationServiceImpl service;

    @Test
    void createUser_shouldDelegate() {
        CreateUserCmd cmd = new CreateUserCmd();
        Response resp = Response.buildSuccess();
        when(createUserCmdExe.execute(cmd)).thenReturn(resp);
        assertSame(resp, service.createUser(cmd));
        verify(createUserCmdExe).execute(cmd);
    }

    @Test
    void updateUser_shouldDelegate() {
        UpdateUserCmd cmd = new UpdateUserCmd();
        Response resp = Response.buildSuccess();
        when(updateUserCmdExe.execute(cmd)).thenReturn(resp);
        assertSame(resp, service.updateUser(cmd));
        verify(updateUserCmdExe).execute(cmd);
    }

    @Test
    void deleteUser_shouldDelegate() {
        DeleteUserCmd cmd = new DeleteUserCmd();
        Response resp = Response.buildSuccess();
        when(deleteUserCmdExe.execute(cmd)).thenReturn(resp);
        assertSame(resp, service.deleteUser(cmd));
        verify(deleteUserCmdExe).execute(cmd);
    }

    @Test
    void assignUserRole_shouldDelegate() {
        AssignUserRoleCmd cmd = new AssignUserRoleCmd();
        Response resp = Response.buildSuccess();
        when(assignUserRoleCmdExe.execute(cmd)).thenReturn(resp);
        assertSame(resp, service.assignUserRole(cmd));
        verify(assignUserRoleCmdExe).execute(cmd);
    }

    @Test
    void getUser_shouldDelegate() {
        GetUserQuery query = new GetUserQuery();
        SingleResponse<UserCO> resp = SingleResponse.of(new UserCO());
        when(getUserQueryExe.execute(query)).thenReturn(resp);
        assertSame(resp, service.getUser(query));
        verify(getUserQueryExe).execute(query);
    }

    @Test
    void pageUser_shouldDelegate() {
        PageUserQuery query = new PageUserQuery();
        PageResponse<UserCO> resp = PageResponse.of(List.of(), 0, 10, 0);
        when(pageUserQueryExe.execute(query)).thenReturn(resp);
        assertSame(resp, service.pageUser(query));
        verify(pageUserQueryExe).execute(query);
    }
}
