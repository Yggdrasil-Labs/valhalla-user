package com.yggdrasil.labs.app.permission.query;

import java.util.List;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.cola.dto.MultiResponse;
import com.yggdrasil.labs.app.permission.assembler.PermissionAssembler;
import com.yggdrasil.labs.client.dto.permission.co.PermissionCO;
import com.yggdrasil.labs.client.dto.permission.query.PagePermissionQuery;
import com.yggdrasil.labs.domain.permission.model.Permission;
import com.yggdrasil.labs.domain.permission.repository.PermissionRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 分页查询权限执行器
 *
 * @author YoungerYang-Y
 */
@Slf4j
@Component
public class PagePermissionQueryExe {

    @Resource private PermissionRepository permissionRepository;

    @Resource private PermissionAssembler permissionAssembler;

    public MultiResponse<PermissionCO> execute(PagePermissionQuery query) {
        log.info(
                "分页查询权限: module={}, permissionName={}, pageNum={}, pageSize={}",
                query.getModule(),
                query.getPermissionName(),
                query.getPageNum(),
                query.getPageSize());

        // 查询权限列表
        List<Permission> permissionList =
                permissionRepository.findPage(
                        query.getModule(),
                        query.getPermissionName(),
                        query.getPageNum(),
                        query.getPageSize());

        // 转换为CO列表
        List<PermissionCO> permissionCOList =
                permissionList.stream()
                        .map(
                                permission -> {
                                    PermissionCO permissionCO =
                                            permissionAssembler.toCO(permission);
                                    // 设置API ID列表
                                    permissionCO.setApiIds(permission.getApiIds());
                                    return permissionCO;
                                })
                        .toList();

        return MultiResponse.of(permissionCOList);
    }
}
