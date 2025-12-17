package com.yggdrasil.labs.app.user.executor;

import java.time.LocalDateTime;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.cola.dto.Response;
import com.yggdrasil.labs.client.dto.enums.ErrorCode;
import com.yggdrasil.labs.client.dto.user.cmd.UpdateUserCmd;
import com.yggdrasil.labs.domain.user.model.User;
import com.yggdrasil.labs.domain.user.model.UserStatus;
import com.yggdrasil.labs.domain.user.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 更新用户命令执行器
 *
 * @author YoungerYang-Y
 */
@Slf4j
@Component
public class UpdateUserCmdExe {

    @Resource private UserRepository userRepository;

    @Transactional(rollbackFor = Exception.class)
    public Response execute(UpdateUserCmd cmd) {
        log.info("更新用户: userId={}", cmd.getId());

        // 查询用户
        User user = userRepository.findById(cmd.getId());
        if (user == null) {
            return Response.buildFailure(
                    ErrorCode.B_USER_NOT_FOUND.getErrCode(),
                    ErrorCode.B_USER_NOT_FOUND.getErrDesc());
        }

        // 检查邮箱是否被其他用户使用
        if (cmd.getEmail() != null && !cmd.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(cmd.getEmail())) {
                return Response.buildFailure(
                        ErrorCode.B_USER_EMAIL_USED_BY_OTHER.getErrCode(),
                        ErrorCode.B_USER_EMAIL_USED_BY_OTHER.getErrDesc());
            }
        }

        // 检查手机号是否被其他用户使用
        if (cmd.getPhone() != null && !cmd.getPhone().equals(user.getPhone())) {
            if (userRepository.existsByPhone(cmd.getPhone())) {
                return Response.buildFailure(
                        ErrorCode.B_USER_PHONE_USED_BY_OTHER.getErrCode(),
                        ErrorCode.B_USER_PHONE_USED_BY_OTHER.getErrDesc());
            }
        }

        // 更新用户信息
        if (cmd.getEmail() != null) {
            user.setEmail(cmd.getEmail());
        }
        if (cmd.getPhone() != null) {
            user.setPhone(cmd.getPhone());
        }
        if (cmd.getNickname() != null) {
            user.setNickname(cmd.getNickname());
        }
        if (cmd.getAvatar() != null) {
            user.setAvatar(cmd.getAvatar());
        }
        if (cmd.getStatus() != null) {
            user.setStatus(UserStatus.fromCode(cmd.getStatus()));
        }
        if (cmd.getMetadata() != null) {
            user.setMetadata(cmd.getMetadata());
        }
        if (cmd.getVersion() != null) {
            user.setVersion(cmd.getVersion());
        }
        user.setUpdateTime(LocalDateTime.now());

        // 更新用户
        userRepository.update(user);

        log.info("用户更新成功: userId={}", user.getId());
        return Response.buildSuccess();
    }
}
