package com.yggdrasil.labs.app.service.impl;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.cola.dto.PageResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.app.service.UserApplicationService;
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

/** 用户应用服务实现 */
@Service
public class UserApplicationServiceImpl implements UserApplicationService {

    @Resource private CreateUserCmdExe createUserCmdExe;

    @Resource private UpdateUserCmdExe updateUserCmdExe;

    @Resource private DeleteUserCmdExe deleteUserCmdExe;

    @Resource private AssignUserRoleCmdExe assignUserRoleCmdExe;

    @Resource private GetUserQueryExe getUserQueryExe;

    @Resource private PageUserQueryExe pageUserQueryExe;

    @Override
    public Response createUser(CreateUserCmd cmd) {
        return createUserCmdExe.execute(cmd);
    }

    @Override
    public Response updateUser(UpdateUserCmd cmd) {
        return updateUserCmdExe.execute(cmd);
    }

    @Override
    public Response deleteUser(DeleteUserCmd cmd) {
        return deleteUserCmdExe.execute(cmd);
    }

    @Override
    public SingleResponse<UserCO> getUser(GetUserQuery query) {
        return getUserQueryExe.execute(query);
    }

    @Override
    public PageResponse<UserCO> pageUser(PageUserQuery query) {
        return pageUserQueryExe.execute(query);
    }

    @Override
    public Response assignUserRole(AssignUserRoleCmd cmd) {
        return assignUserRoleCmdExe.execute(cmd);
    }
}
