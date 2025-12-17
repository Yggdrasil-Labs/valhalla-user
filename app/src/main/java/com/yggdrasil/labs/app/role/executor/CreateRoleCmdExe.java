package com.yggdrasil.labs.app.role.executor;

import java.time.LocalDateTime;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.cola.dto.Response;
import com.yggdrasil.labs.client.dto.enums.ErrorCode;
import com.yggdrasil.labs.client.dto.role.cmd.CreateRoleCmd;
import com.yggdrasil.labs.domain.role.model.Role;
import com.yggdrasil.labs.domain.role.repository.RoleRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 创建角色命令执行器
 *
 * @author YoungerYang-Y
 */
@Slf4j
@Component
public class CreateRoleCmdExe {

    @Resource private RoleRepository roleRepository;

    @Transactional(rollbackFor = Exception.class)
    public Response execute(CreateRoleCmd cmd) {
        log.info("创建角色: roleCode={}", cmd.getRoleCode());

        // 检查角色代码是否已存在
        if (roleRepository.existsByRoleCode(cmd.getRoleCode())) {
            return Response.buildFailure(
                    ErrorCode.B_ROLE_CODE_EXISTS.getErrCode(),
                    ErrorCode.B_ROLE_CODE_EXISTS.getErrDesc());
        }

        // 创建角色实体
        Role role = new Role();
        role.setRoleCode(cmd.getRoleCode());
        role.setRoleName(cmd.getRoleName());
        role.setDescription(cmd.getDescription());
        role.setIsSystem(cmd.getIsSystem() != null && cmd.getIsSystem());
        role.setMetadata(cmd.getMetadata());
        role.setCreateTime(LocalDateTime.now());
        role.setUpdateTime(LocalDateTime.now());

        // 分配权限
        if (cmd.getPermissionIds() != null && !cmd.getPermissionIds().isEmpty()) {
            role.assignPermissions(cmd.getPermissionIds());
        }

        // 保存角色
        roleRepository.save(role);

        log.info("角色创建成功: roleId={}, roleCode={}", role.getId(), role.getRoleCode());
        return Response.buildSuccess();
    }
}
