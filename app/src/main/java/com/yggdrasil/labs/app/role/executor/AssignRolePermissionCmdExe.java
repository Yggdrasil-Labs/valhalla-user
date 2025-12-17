package com.yggdrasil.labs.app.role.executor;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.cola.dto.Response;
import com.yggdrasil.labs.client.dto.enums.ErrorCode;
import com.yggdrasil.labs.client.dto.role.cmd.AssignRolePermissionCmd;
import com.yggdrasil.labs.domain.role.model.Role;
import com.yggdrasil.labs.domain.role.repository.RoleRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 分配角色权限命令执行器
 *
 * @author YoungerYang-Y
 */
@Slf4j
@Component
public class AssignRolePermissionCmdExe {

    @Resource private RoleRepository roleRepository;

    @Transactional(rollbackFor = Exception.class)
    public Response execute(AssignRolePermissionCmd cmd) {
        log.info("分配角色权限: roleId={}, permissionIds={}", cmd.getRoleId(), cmd.getPermissionIds());

        // 查询角色
        Role role = roleRepository.findById(cmd.getRoleId());
        if (role == null) {
            return Response.buildFailure(
                    ErrorCode.B_ROLE_NOT_FOUND.getErrCode(),
                    ErrorCode.B_ROLE_NOT_FOUND.getErrDesc());
        }

        // 分配权限
        role.assignPermissions(cmd.getPermissionIds());

        // 更新角色
        roleRepository.update(role);

        log.info("角色权限分配成功: roleId={}, permissionIds={}", cmd.getRoleId(), cmd.getPermissionIds());
        return Response.buildSuccess();
    }
}
