package com.yggdrasil.labs.app.permission.executor;

import java.time.LocalDateTime;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.cola.dto.Response;
import com.yggdrasil.labs.client.dto.enums.ErrorCode;
import com.yggdrasil.labs.client.dto.permission.cmd.UpdatePermissionCmd;
import com.yggdrasil.labs.domain.permission.model.Permission;
import com.yggdrasil.labs.domain.permission.repository.PermissionRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 更新权限命令执行器
 *
 * @author YoungerYang-Y
 */
@Slf4j
@Component
public class UpdatePermissionCmdExe {

    @Resource private PermissionRepository permissionRepository;

    @Transactional(rollbackFor = Exception.class)
    public Response execute(UpdatePermissionCmd cmd) {
        log.info("更新权限: permissionId={}", cmd.getId());

        // 查询权限
        Permission permission = permissionRepository.findById(cmd.getId());
        if (permission == null) {
            return Response.buildFailure(
                    ErrorCode.B_PERMISSION_NOT_FOUND.getErrCode(),
                    ErrorCode.B_PERMISSION_NOT_FOUND.getErrDesc());
        }

        // 更新权限信息
        if (cmd.getPermissionName() != null) {
            permission.setPermissionName(cmd.getPermissionName());
        }
        if (cmd.getDescription() != null) {
            permission.setDescription(cmd.getDescription());
        }
        if (cmd.getMetadata() != null) {
            permission.setMetadata(cmd.getMetadata());
        }
        permission.setUpdateTime(LocalDateTime.now());

        // 更新权限
        permissionRepository.update(permission);

        log.info("权限更新成功: permissionId={}", permission.getId());
        return Response.buildSuccess();
    }
}
