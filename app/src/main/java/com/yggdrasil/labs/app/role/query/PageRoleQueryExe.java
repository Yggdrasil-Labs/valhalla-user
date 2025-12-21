package com.yggdrasil.labs.app.role.query;

import java.util.List;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.cola.dto.MultiResponse;
import com.yggdrasil.labs.app.role.assembler.RoleAssembler;
import com.yggdrasil.labs.client.dto.role.co.RoleCO;
import com.yggdrasil.labs.client.dto.role.query.PageRoleQuery;
import com.yggdrasil.labs.domain.role.model.Role;
import com.yggdrasil.labs.domain.role.repository.RoleRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 分页查询角色执行器
 *
 * @author YoungerYang-Y
 */
@Slf4j
@Component
public class PageRoleQueryExe {

    @Resource private RoleRepository roleRepository;

    @Resource private RoleAssembler roleAssembler;

    public MultiResponse<RoleCO> execute(PageRoleQuery query) {
        log.info(
                "分页查询角色: roleName={}, roleCode={}, pageNum={}, pageSize={}",
                query.getRoleName(),
                query.getRoleCode(),
                query.getPageNum(),
                query.getPageSize());

        // 查询角色列表
        List<Role> roleList =
                roleRepository.findPage(
                        query.getRoleName(),
                        query.getRoleCode(),
                        query.getPageNum(),
                        query.getPageSize());

        // 转换为CO列表
        List<RoleCO> roleCOList =
                roleList.stream()
                        .map(
                                role -> {
                                    RoleCO roleCO = roleAssembler.toCO(role);
                                    // 设置权限ID列表（转换为String类型）
                                    if (role.getPermissionIds() != null) {
                                        roleCO.setPermissionIds(
                                                role.getPermissionIds().stream()
                                                        .map(String::valueOf)
                                                        .toList());
                                    }
                                    return roleCO;
                                })
                        .toList();

        return MultiResponse.of(roleCOList);
    }
}
