package com.yggdrasil.labs.app.service;

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

/** 用户应用服务 */
public interface UserApplicationService {

    Response createUser(CreateUserCmd cmd);

    Response updateUser(UpdateUserCmd cmd);

    Response deleteUser(DeleteUserCmd cmd);

    SingleResponse<UserCO> getUser(GetUserQuery query);

    PageResponse<UserCO> pageUser(PageUserQuery query);

    Response assignUserRole(AssignUserRoleCmd cmd);
}
