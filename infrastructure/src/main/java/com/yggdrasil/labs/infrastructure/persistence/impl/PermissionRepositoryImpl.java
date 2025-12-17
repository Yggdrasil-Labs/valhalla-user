package com.yggdrasil.labs.infrastructure.persistence.impl;

import java.util.List;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yggdrasil.labs.domain.permission.model.Permission;
import com.yggdrasil.labs.domain.permission.repository.PermissionRepository;
import com.yggdrasil.labs.infrastructure.persistence.converter.PermissionConverter;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.PermissionApiDO;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.PermissionDO;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.service.PermissionApiService;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.service.PermissionService;

/**
 * 权限仓储实现
 *
 * @author YoungerYang-Y
 */
@Repository
public class PermissionRepositoryImpl implements PermissionRepository {

    @Resource private PermissionService permissionService;

    @Resource private PermissionApiService permissionApiService;

    @Resource private PermissionConverter permissionConverter;

    @Override
    public Permission findById(Long id) {
        PermissionDO permissionDO = permissionService.getById(id);
        if (permissionDO == null) {
            return null;
        }
        Permission permission = permissionConverter.toEntity(permissionDO);
        // 加载API ID列表
        List<Long> apiIds = findApiIdsByPermissionId(id);
        permission.setApiIds(apiIds);
        return permission;
    }

    @Override
    public Permission findByPermissionCode(String permissionCode) {
        LambdaQueryWrapper<PermissionDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PermissionDO::getPermissionCode, permissionCode);
        PermissionDO permissionDO = permissionService.getOne(wrapper);
        if (permissionDO == null) {
            return null;
        }
        Permission permission = permissionConverter.toEntity(permissionDO);
        List<Long> apiIds = findApiIdsByPermissionId(permissionDO.getId());
        permission.setApiIds(apiIds);
        return permission;
    }

    @Override
    public Permission findByModuleResourceAction(String module, String resource, String action) {
        LambdaQueryWrapper<PermissionDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PermissionDO::getModule, module)
                .eq(PermissionDO::getResource, resource)
                .eq(PermissionDO::getAction, action);
        PermissionDO permissionDO = permissionService.getOne(wrapper);
        if (permissionDO == null) {
            return null;
        }
        Permission permission = permissionConverter.toEntity(permissionDO);
        List<Long> apiIds = findApiIdsByPermissionId(permissionDO.getId());
        permission.setApiIds(apiIds);
        return permission;
    }

    @Override
    public void save(Permission permission) {
        PermissionDO permissionDO = permissionConverter.toDO(permission);
        permissionService.save(permissionDO);
        permission.setId(permissionDO.getId());
        // 保存权限API关联
        if (permission.getApiIds() != null && !permission.getApiIds().isEmpty()) {
            savePermissionApis(permission.getId(), permission.getApiIds());
        }
    }

    @Override
    public void update(Permission permission) {
        PermissionDO permissionDO = permissionConverter.toDO(permission);
        permissionService.updateById(permissionDO);
        // 更新权限API关联
        deletePermissionApis(permission.getId());
        if (permission.getApiIds() != null && !permission.getApiIds().isEmpty()) {
            savePermissionApis(permission.getId(), permission.getApiIds());
        }
    }

    @Override
    public void delete(Long id) {
        permissionService.removeById(id);
        // 删除权限API关联
        deletePermissionApis(id);
    }

    @Override
    public List<Permission> findPage(
            String module, String permissionName, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<PermissionDO> wrapper = new LambdaQueryWrapper<>();
        if (module != null && !module.trim().isEmpty()) {
            wrapper.eq(PermissionDO::getModule, module);
        }
        if (permissionName != null && !permissionName.trim().isEmpty()) {
            wrapper.like(PermissionDO::getPermissionName, permissionName);
        }
        Page<PermissionDO> page = new Page<>(pageNum, pageSize);
        IPage<PermissionDO> pageResult = permissionService.page(page, wrapper);
        List<PermissionDO> permissionDOList = pageResult.getRecords();
        return permissionDOList.stream()
                .map(
                        permissionDO -> {
                            Permission permission = permissionConverter.toEntity(permissionDO);
                            List<Long> apiIds = findApiIdsByPermissionId(permissionDO.getId());
                            permission.setApiIds(apiIds);
                            return permission;
                        })
                .toList();
    }

    @Override
    public long count(String module, String permissionName) {
        LambdaQueryWrapper<PermissionDO> wrapper = new LambdaQueryWrapper<>();
        if (module != null && !module.trim().isEmpty()) {
            wrapper.eq(PermissionDO::getModule, module);
        }
        if (permissionName != null && !permissionName.trim().isEmpty()) {
            wrapper.like(PermissionDO::getPermissionName, permissionName);
        }
        return permissionService.count(wrapper);
    }

    @Override
    public boolean existsByPermissionCode(String permissionCode) {
        LambdaQueryWrapper<PermissionDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PermissionDO::getPermissionCode, permissionCode);
        return permissionService.count(wrapper) > 0;
    }

    @Override
    public boolean existsByModuleResourceAction(String module, String resource, String action) {
        LambdaQueryWrapper<PermissionDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PermissionDO::getModule, module)
                .eq(PermissionDO::getResource, resource)
                .eq(PermissionDO::getAction, action);
        return permissionService.count(wrapper) > 0;
    }

    @Override
    public void savePermissionApis(Long permissionId, List<Long> apiIds) {
        if (apiIds == null || apiIds.isEmpty()) {
            return;
        }
        for (Long apiId : apiIds) {
            PermissionApiDO permissionApiDO = new PermissionApiDO();
            permissionApiDO.setPermissionId(permissionId);
            permissionApiDO.setApiId(apiId);
            permissionApiService.save(permissionApiDO);
        }
    }

    @Override
    public void deletePermissionApis(Long permissionId) {
        LambdaQueryWrapper<PermissionApiDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PermissionApiDO::getPermissionId, permissionId);
        permissionApiService.remove(wrapper);
    }

    @Override
    public List<Long> findApiIdsByPermissionId(Long permissionId) {
        LambdaQueryWrapper<PermissionApiDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PermissionApiDO::getPermissionId, permissionId);
        List<PermissionApiDO> permissionApiDOList = permissionApiService.list(wrapper);
        return permissionApiDOList.stream().map(PermissionApiDO::getApiId).toList();
    }
}
