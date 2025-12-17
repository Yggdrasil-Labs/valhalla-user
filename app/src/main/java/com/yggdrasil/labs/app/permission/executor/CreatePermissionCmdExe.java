package com.yggdrasil.labs.app.permission.executor;

import java.time.LocalDateTime;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.cola.dto.Response;
import com.yggdrasil.labs.client.dto.enums.ErrorCode;
import com.yggdrasil.labs.client.dto.permission.cmd.CreatePermissionCmd;
import com.yggdrasil.labs.domain.permission.model.Permission;
import com.yggdrasil.labs.domain.permission.repository.PermissionRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 创建权限命令执行器
 *
 * @author YoungerYang-Y
 */
@Slf4j
@Component
public class CreatePermissionCmdExe {

    @Resource private PermissionRepository permissionRepository;

    @Transactional(rollbackFor = Exception.class)
    public Response execute(CreatePermissionCmd cmd) {
        log.info(
                "创建权限: module={}, resource={}, action={}",
                cmd.getModule(),
                cmd.getResource(),
                cmd.getAction());

        // 检查模块、资源、操作组合是否已存在
        if (permissionRepository.existsByModuleResourceAction(
                cmd.getModule(), cmd.getResource(), cmd.getAction())) {
            return Response.buildFailure(
                    ErrorCode.B_PERMISSION_EXISTS.getErrCode(),
                    ErrorCode.B_PERMISSION_EXISTS.getErrDesc());
        }

        // 创建权限实体
        Permission permission = new Permission();
        permission.setModule(cmd.getModule());
        permission.setResource(cmd.getResource());
        permission.setAction(cmd.getAction());
        permission.setPermissionName(cmd.getPermissionName());
        permission.setDescription(cmd.getDescription());
        permission.setMetadata(cmd.getMetadata());
        permission.setCreateTime(LocalDateTime.now());
        permission.setUpdateTime(LocalDateTime.now());

        // 生成权限代码
        permission.generatePermissionCode();

        // 分配API
        if (cmd.getApiIds() != null && !cmd.getApiIds().isEmpty()) {
            permission.assignApis(cmd.getApiIds());
        }

        // 保存权限
        permissionRepository.save(permission);

        log.info(
                "权限创建成功: permissionId={}, permissionCode={}",
                permission.getId(),
                permission.getPermissionCode());
        return Response.buildSuccess();
    }
}
