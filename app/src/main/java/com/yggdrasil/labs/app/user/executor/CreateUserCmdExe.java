package com.yggdrasil.labs.app.user.executor;

import java.time.LocalDateTime;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.cola.dto.Response;
import com.yggdrasil.labs.client.dto.enums.ErrorCode;
import com.yggdrasil.labs.client.dto.user.cmd.CreateUserCmd;
import com.yggdrasil.labs.domain.user.model.User;
import com.yggdrasil.labs.domain.user.model.UserStatus;
import com.yggdrasil.labs.domain.user.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 创建用户命令执行器
 *
 * @author YoungerYang-Y
 */
@Slf4j
@Component
public class CreateUserCmdExe {

    @Resource private UserRepository userRepository;

    @Transactional(rollbackFor = Exception.class)
    public Response execute(CreateUserCmd cmd) {
        log.info("创建用户: username={}", cmd.getUsername());

        // 检查用户名是否已存在
        if (userRepository.existsByUsername(cmd.getUsername())) {
            return Response.buildFailure(
                    ErrorCode.B_USER_USERNAME_EXISTS.getErrCode(),
                    ErrorCode.B_USER_USERNAME_EXISTS.getErrDesc());
        }

        // 检查邮箱是否已存在
        if (cmd.getEmail() != null && userRepository.existsByEmail(cmd.getEmail())) {
            return Response.buildFailure(
                    ErrorCode.B_USER_EMAIL_EXISTS.getErrCode(),
                    ErrorCode.B_USER_EMAIL_EXISTS.getErrDesc());
        }

        // 检查手机号是否已存在
        if (cmd.getPhone() != null && userRepository.existsByPhone(cmd.getPhone())) {
            return Response.buildFailure(
                    ErrorCode.B_USER_PHONE_EXISTS.getErrCode(),
                    ErrorCode.B_USER_PHONE_EXISTS.getErrDesc());
        }

        // 创建用户实体
        User user = new User();
        user.setUsername(cmd.getUsername());
        user.setEmail(cmd.getEmail());
        user.setPhone(cmd.getPhone());
        user.setNickname(cmd.getNickname());
        user.setAvatar(cmd.getAvatar());
        user.setMetadata(cmd.getMetadata());
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        // 设置状态（默认启用）
        if (cmd.getStatus() != null) {
            user.setStatus(UserStatus.fromCode(cmd.getStatus()));
        } else {
            user.setStatus(UserStatus.ENABLED);
        }

        // 分配角色
        if (cmd.getRoleIds() != null && !cmd.getRoleIds().isEmpty()) {
            user.assignRoles(cmd.getRoleIds());
        }

        // 保存用户
        userRepository.save(user);

        log.info("用户创建成功: userId={}, username={}", user.getId(), user.getUsername());
        return Response.buildSuccess();
    }
}
