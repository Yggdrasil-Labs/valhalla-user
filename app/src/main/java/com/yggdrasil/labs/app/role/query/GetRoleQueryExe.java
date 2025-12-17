package com.yggdrasil.labs.app.role.query;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.app.role.assembler.RoleAssembler;
import com.yggdrasil.labs.client.dto.enums.ErrorCode;
import com.yggdrasil.labs.client.dto.role.co.RoleCO;
import com.yggdrasil.labs.client.dto.role.query.GetRoleQuery;
import com.yggdrasil.labs.domain.role.model.Role;
import com.yggdrasil.labs.domain.role.repository.RoleRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 获取角色查询执行器
 *
 * @author YoungerYang-Y
 */
@Slf4j
@Component
public class GetRoleQueryExe {

    @Resource private RoleRepository roleRepository;

    @Resource private RoleAssembler roleAssembler;

    public SingleResponse<RoleCO> execute(GetRoleQuery query) {
        log.info("获取角色详情: roleId={}", query.getId());

        // 查询角色
        Role role = roleRepository.findById(query.getId());
        if (role == null) {
            return SingleResponse.buildFailure(
                    ErrorCode.B_ROLE_NOT_FOUND.getErrCode(),
                    ErrorCode.B_ROLE_NOT_FOUND.getErrDesc());
        }

        // 转换为CO
        RoleCO roleCO = roleAssembler.toCO(role);
        // 设置权限ID列表
        roleCO.setPermissionIds(role.getPermissionIds());

        return SingleResponse.of(roleCO);
    }
}
