package com.yggdrasil.labs.app.permission.query;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.app.permission.assembler.PermissionAssembler;
import com.yggdrasil.labs.client.dto.enums.ErrorCode;
import com.yggdrasil.labs.client.dto.permission.co.PermissionCO;
import com.yggdrasil.labs.client.dto.permission.query.GetPermissionQuery;
import com.yggdrasil.labs.domain.permission.model.Permission;
import com.yggdrasil.labs.domain.permission.repository.PermissionRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 获取权限查询执行器
 *
 * @author YoungerYang-Y
 */
@Slf4j
@Component
public class GetPermissionQueryExe {

    @Resource private PermissionRepository permissionRepository;

    @Resource private PermissionAssembler permissionAssembler;

    public SingleResponse<PermissionCO> execute(GetPermissionQuery query) {
        log.info("获取权限详情: permissionId={}", query.getId());

        // 查询权限
        Permission permission = permissionRepository.findById(query.getId());
        if (permission == null) {
            return SingleResponse.buildFailure(
                    ErrorCode.B_PERMISSION_NOT_FOUND.getErrCode(),
                    ErrorCode.B_PERMISSION_NOT_FOUND.getErrDesc());
        }

        // 转换为CO
        PermissionCO permissionCO = permissionAssembler.toCO(permission);
        // 设置API ID列表
        permissionCO.setApiIds(permission.getApiIds());

        return SingleResponse.of(permissionCO);
    }
}
