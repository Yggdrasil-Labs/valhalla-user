package com.yggdrasil.labs.app.permission.query;

import java.util.List;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.cola.dto.PageResponse;
import com.yggdrasil.labs.app.permission.assembler.PermissionAssembler;
import com.yggdrasil.labs.client.dto.permission.co.PermissionCO;
import com.yggdrasil.labs.client.dto.permission.query.PagePermissionQuery;
import com.yggdrasil.labs.domain.common.PageResult;
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

    public PageResponse<PermissionCO> execute(PagePermissionQuery query) {
        log.info(
                "分页查询权限: module={}, permissionName={}, pageNum={}, pageSize={}",
                query.getModule(),
                query.getPermissionName(),
                query.getPageNum(),
                query.getPageSize());

        // 查询权限列表（包含总数）
        PageResult<Permission> pageResult =
                permissionRepository.findPage(
                        query.getModule(),
                        query.getPermissionName(),
                        query.getPageNum(),
                        query.getPageSize());

        // 转换为CO列表
        List<PermissionCO> permissionCOList =
                pageResult.getData().stream()
                        .map(
                                permission -> {
                                    PermissionCO permissionCO =
                                            permissionAssembler.toCO(permission);
                                    // 设置API ID列表（转换为String类型）
                                    if (permission.getApiIds() != null) {
                                        permissionCO.setApiIds(
                                                permission.getApiIds().stream()
                                                        .map(String::valueOf)
                                                        .toList());
                                    }
                                    return permissionCO;
                                })
                        .toList();

        return PageResponse.of(
                permissionCOList,
                (int) pageResult.getTotal(),
                query.getPageSize(),
                query.getPageNum() - 1);
    }
}
