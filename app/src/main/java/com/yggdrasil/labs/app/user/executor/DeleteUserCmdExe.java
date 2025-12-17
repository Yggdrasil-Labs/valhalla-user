package com.yggdrasil.labs.app.user.executor;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.cola.dto.Response;
import com.yggdrasil.labs.client.dto.enums.ErrorCode;
import com.yggdrasil.labs.client.dto.user.cmd.DeleteUserCmd;
import com.yggdrasil.labs.domain.user.model.User;
import com.yggdrasil.labs.domain.user.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 删除用户命令执行器
 *
 * @author YoungerYang-Y
 */
@Slf4j
@Component
public class DeleteUserCmdExe {

    @Resource private UserRepository userRepository;

    @Transactional(rollbackFor = Exception.class)
    public Response execute(DeleteUserCmd cmd) {
        log.info("删除用户: userId={}", cmd.getId());

        // 查询用户
        User user = userRepository.findById(cmd.getId());
        if (user == null) {
            return Response.buildFailure(
                    ErrorCode.B_USER_NOT_FOUND.getErrCode(),
                    ErrorCode.B_USER_NOT_FOUND.getErrDesc());
        }

        // 删除用户（软删除）
        userRepository.delete(cmd.getId());

        log.info("用户删除成功: userId={}", cmd.getId());
        return Response.buildSuccess();
    }
}
