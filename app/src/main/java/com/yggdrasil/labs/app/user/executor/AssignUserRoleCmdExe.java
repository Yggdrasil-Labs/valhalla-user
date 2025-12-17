package com.yggdrasil.labs.app.user.executor;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.cola.dto.Response;
import com.yggdrasil.labs.client.dto.enums.ErrorCode;
import com.yggdrasil.labs.client.dto.user.cmd.AssignUserRoleCmd;
import com.yggdrasil.labs.domain.user.model.User;
import com.yggdrasil.labs.domain.user.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 分配用户角色命令执行器
 *
 * @author YoungerYang-Y
 */
@Slf4j
@Component
public class AssignUserRoleCmdExe {

    @Resource private UserRepository userRepository;

    @Transactional(rollbackFor = Exception.class)
    public Response execute(AssignUserRoleCmd cmd) {
        log.info("分配用户角色: userId={}, roleIds={}", cmd.getUserId(), cmd.getRoleIds());

        // 查询用户
        User user = userRepository.findById(cmd.getUserId());
        if (user == null) {
            return Response.buildFailure(
                    ErrorCode.B_USER_NOT_FOUND.getErrCode(),
                    ErrorCode.B_USER_NOT_FOUND.getErrDesc());
        }

        // 分配角色
        user.assignRoles(cmd.getRoleIds());

        // 更新用户
        userRepository.update(user);

        log.info("用户角色分配成功: userId={}, roleIds={}", cmd.getUserId(), cmd.getRoleIds());
        return Response.buildSuccess();
    }
}
