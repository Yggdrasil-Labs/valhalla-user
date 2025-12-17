package com.yggdrasil.labs.app.permission.executor;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.cola.dto.Response;
import com.yggdrasil.labs.client.dto.enums.ErrorCode;
import com.yggdrasil.labs.client.dto.permission.cmd.DeletePermissionCmd;
import com.yggdrasil.labs.domain.permission.model.Permission;
import com.yggdrasil.labs.domain.permission.repository.PermissionRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 删除权限命令执行器
 *
 * @author YoungerYang-Y
 */
@Slf4j
@Component
public class DeletePermissionCmdExe {

    @Resource private PermissionRepository permissionRepository;

    @Transactional(rollbackFor = Exception.class)
    public Response execute(DeletePermissionCmd cmd) {
        log.info("删除权限: permissionId={}", cmd.getId());

        // 查询权限
        Permission permission = permissionRepository.findById(cmd.getId());
        if (permission == null) {
            return Response.buildFailure(
                    ErrorCode.B_PERMISSION_NOT_FOUND.getErrCode(),
                    ErrorCode.B_PERMISSION_NOT_FOUND.getErrDesc());
        }

        // 删除权限（软删除）
        permissionRepository.delete(cmd.getId());

        log.info("权限删除成功: permissionId={}", cmd.getId());
        return Response.buildSuccess();
    }
}
