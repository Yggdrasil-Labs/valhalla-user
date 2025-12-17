package com.yggdrasil.labs.app.role.executor;

import java.time.LocalDateTime;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.cola.dto.Response;
import com.yggdrasil.labs.client.dto.enums.ErrorCode;
import com.yggdrasil.labs.client.dto.role.cmd.UpdateRoleCmd;
import com.yggdrasil.labs.domain.role.model.Role;
import com.yggdrasil.labs.domain.role.repository.RoleRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 更新角色命令执行器
 *
 * @author YoungerYang-Y
 */
@Slf4j
@Component
public class UpdateRoleCmdExe {

    @Resource private RoleRepository roleRepository;

    @Transactional(rollbackFor = Exception.class)
    public Response execute(UpdateRoleCmd cmd) {
        log.info("更新角色: roleId={}", cmd.getId());

        // 查询角色
        Role role = roleRepository.findById(cmd.getId());
        if (role == null) {
            return Response.buildFailure(
                    ErrorCode.B_ROLE_NOT_FOUND.getErrCode(),
                    ErrorCode.B_ROLE_NOT_FOUND.getErrDesc());
        }

        // 更新角色信息
        if (cmd.getRoleName() != null) {
            role.setRoleName(cmd.getRoleName());
        }
        if (cmd.getDescription() != null) {
            role.setDescription(cmd.getDescription());
        }
        if (cmd.getMetadata() != null) {
            role.setMetadata(cmd.getMetadata());
        }
        role.setUpdateTime(LocalDateTime.now());

        // 更新角色
        roleRepository.update(role);

        log.info("角色更新成功: roleId={}", role.getId());
        return Response.buildSuccess();
    }
}
