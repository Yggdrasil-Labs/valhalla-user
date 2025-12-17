package com.yggdrasil.labs.app.user;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.app.user.executor.AssignUserRoleCmdExe;
import com.yggdrasil.labs.app.user.executor.CreateUserCmdExe;
import com.yggdrasil.labs.app.user.executor.DeleteUserCmdExe;
import com.yggdrasil.labs.app.user.executor.UpdateUserCmdExe;
import com.yggdrasil.labs.app.user.query.GetUserQueryExe;
import com.yggdrasil.labs.app.user.query.PageUserQueryExe;
import com.yggdrasil.labs.client.api.UserClient;
import com.yggdrasil.labs.client.dto.user.cmd.AssignUserRoleCmd;
import com.yggdrasil.labs.client.dto.user.cmd.CreateUserCmd;
import com.yggdrasil.labs.client.dto.user.cmd.DeleteUserCmd;
import com.yggdrasil.labs.client.dto.user.cmd.UpdateUserCmd;
import com.yggdrasil.labs.client.dto.user.co.UserCO;
import com.yggdrasil.labs.client.dto.user.query.GetUserQuery;
import com.yggdrasil.labs.client.dto.user.query.PageUserQuery;

/**
 * 用户客户端实现
 *
 * @author YoungerYang-Y
 */
@Service
public class UserClientImpl implements UserClient {

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
    public MultiResponse<UserCO> pageUser(PageUserQuery query) {
        return pageUserQueryExe.execute(query);
    }

    @Override
    public Response assignUserRole(AssignUserRoleCmd cmd) {
        return assignUserRoleCmdExe.execute(cmd);
    }
}
