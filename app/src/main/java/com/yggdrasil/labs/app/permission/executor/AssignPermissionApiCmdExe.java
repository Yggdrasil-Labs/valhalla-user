package com.yggdrasil.labs.app.permission.executor;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.cola.dto.Response;
import com.yggdrasil.labs.client.dto.enums.ErrorCode;
import com.yggdrasil.labs.client.dto.permission.cmd.AssignPermissionApiCmd;
import com.yggdrasil.labs.domain.permission.model.Permission;
import com.yggdrasil.labs.domain.permission.repository.PermissionRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 分配权限API命令执行器
 *
 * @author YoungerYang-Y
 */
@Slf4j
@Component
public class AssignPermissionApiCmdExe {

    @Resource private PermissionRepository permissionRepository;

    @Transactional(rollbackFor = Exception.class)
    public Response execute(AssignPermissionApiCmd cmd) {
        log.info("分配权限API: permissionId={}, apiIds={}", cmd.getPermissionId(), cmd.getApiIds());

        // 查询权限
        Permission permission = permissionRepository.findById(cmd.getPermissionId());
        if (permission == null) {
            return Response.buildFailure(
                    ErrorCode.B_PERMISSION_NOT_FOUND.getErrCode(),
                    ErrorCode.B_PERMISSION_NOT_FOUND.getErrDesc());
        }

        // 分配API
        permission.assignApis(cmd.getApiIds());

        // 更新权限
        permissionRepository.update(permission);

        log.info("权限API分配成功: permissionId={}, apiIds={}", cmd.getPermissionId(), cmd.getApiIds());
        return Response.buildSuccess();
    }
}
