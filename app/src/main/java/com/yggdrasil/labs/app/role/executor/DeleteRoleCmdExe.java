package com.yggdrasil.labs.app.role.executor;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.cola.dto.Response;
import com.yggdrasil.labs.client.dto.enums.ErrorCode;
import com.yggdrasil.labs.client.dto.role.cmd.DeleteRoleCmd;
import com.yggdrasil.labs.domain.role.model.Role;
import com.yggdrasil.labs.domain.role.repository.RoleRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 删除角色命令执行器
 *
 * @author YoungerYang-Y
 */
@Slf4j
@Component
public class DeleteRoleCmdExe {

    @Resource private RoleRepository roleRepository;

    @Transactional(rollbackFor = Exception.class)
    public Response execute(DeleteRoleCmd cmd) {
        log.info("删除角色: roleId={}", cmd.getId());

        // 查询角色
        Role role = roleRepository.findById(cmd.getId());
        if (role == null) {
            return Response.buildFailure(
                    ErrorCode.B_ROLE_NOT_FOUND.getErrCode(),
                    ErrorCode.B_ROLE_NOT_FOUND.getErrDesc());
        }

        // 检查是否为系统角色
        if (!role.canBeDeleted()) {
            return Response.buildFailure(
                    ErrorCode.B_ROLE_IS_SYSTEM.getErrCode(),
                    ErrorCode.B_ROLE_IS_SYSTEM.getErrDesc());
        }

        // 删除角色（软删除）
        roleRepository.delete(cmd.getId());

        log.info("角色删除成功: roleId={}", cmd.getId());
        return Response.buildSuccess();
    }
}
