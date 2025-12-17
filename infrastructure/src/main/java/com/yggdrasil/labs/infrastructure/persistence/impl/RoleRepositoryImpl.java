package com.yggdrasil.labs.infrastructure.persistence.impl;

import java.util.List;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yggdrasil.labs.domain.role.model.Role;
import com.yggdrasil.labs.domain.role.repository.RoleRepository;
import com.yggdrasil.labs.infrastructure.persistence.converter.RoleConverter;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.RoleDO;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.RolePermissionDO;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.service.RolePermissionService;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.service.RoleService;

/**
 * 角色仓储实现
 *
 * @author YoungerYang-Y
 */
@Repository
public class RoleRepositoryImpl implements RoleRepository {

    @Resource private RoleService roleService;

    @Resource private RolePermissionService rolePermissionService;

    @Resource private RoleConverter roleConverter;

    @Override
    public Role findById(Long id) {
        RoleDO roleDO = roleService.getById(id);
        if (roleDO == null) {
            return null;
        }
        Role role = roleConverter.toEntity(roleDO);
        // 加载权限ID列表
        List<Long> permissionIds = findPermissionIdsByRoleId(id);
        role.setPermissionIds(permissionIds);
        return role;
    }

    @Override
    public Role findByRoleCode(String roleCode) {
        LambdaQueryWrapper<RoleDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleDO::getRoleCode, roleCode);
        RoleDO roleDO = roleService.getOne(wrapper);
        if (roleDO == null) {
            return null;
        }
        Role role = roleConverter.toEntity(roleDO);
        List<Long> permissionIds = findPermissionIdsByRoleId(roleDO.getId());
        role.setPermissionIds(permissionIds);
        return role;
    }

    @Override
    public void save(Role role) {
        RoleDO roleDO = roleConverter.toDO(role);
        roleService.save(roleDO);
        role.setId(roleDO.getId());
        // 保存角色权限关联
        if (role.getPermissionIds() != null && !role.getPermissionIds().isEmpty()) {
            saveRolePermissions(role.getId(), role.getPermissionIds());
        }
    }

    @Override
    public void update(Role role) {
        RoleDO roleDO = roleConverter.toDO(role);
        roleService.updateById(roleDO);
        // 更新角色权限关联
        deleteRolePermissions(role.getId());
        if (role.getPermissionIds() != null && !role.getPermissionIds().isEmpty()) {
            saveRolePermissions(role.getId(), role.getPermissionIds());
        }
    }

    @Override
    public void delete(Long id) {
        roleService.removeById(id);
        // 删除角色权限关联
        deleteRolePermissions(id);
    }

    @Override
    public List<Role> findPage(
            String roleName, String roleCode, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<RoleDO> wrapper = new LambdaQueryWrapper<>();
        if (roleName != null && !roleName.trim().isEmpty()) {
            wrapper.like(RoleDO::getRoleName, roleName);
        }
        if (roleCode != null && !roleCode.trim().isEmpty()) {
            wrapper.like(RoleDO::getRoleCode, roleCode);
        }
        Page<RoleDO> page = new Page<>(pageNum, pageSize);
        IPage<RoleDO> pageResult = roleService.page(page, wrapper);
        List<RoleDO> roleDOList = pageResult.getRecords();
        return roleDOList.stream()
                .map(
                        roleDO -> {
                            Role role = roleConverter.toEntity(roleDO);
                            List<Long> permissionIds = findPermissionIdsByRoleId(roleDO.getId());
                            role.setPermissionIds(permissionIds);
                            return role;
                        })
                .toList();
    }

    @Override
    public long count(String roleName, String roleCode) {
        LambdaQueryWrapper<RoleDO> wrapper = new LambdaQueryWrapper<>();
        if (roleName != null && !roleName.trim().isEmpty()) {
            wrapper.like(RoleDO::getRoleName, roleName);
        }
        if (roleCode != null && !roleCode.trim().isEmpty()) {
            wrapper.like(RoleDO::getRoleCode, roleCode);
        }
        return roleService.count(wrapper);
    }

    @Override
    public boolean existsByRoleCode(String roleCode) {
        LambdaQueryWrapper<RoleDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleDO::getRoleCode, roleCode);
        return roleService.count(wrapper) > 0;
    }

    @Override
    public void saveRolePermissions(Long roleId, List<Long> permissionIds) {
        if (permissionIds == null || permissionIds.isEmpty()) {
            return;
        }
        for (Long permissionId : permissionIds) {
            RolePermissionDO rolePermissionDO = new RolePermissionDO();
            rolePermissionDO.setRoleId(roleId);
            rolePermissionDO.setPermissionId(permissionId);
            rolePermissionService.save(rolePermissionDO);
        }
    }

    @Override
    public void deleteRolePermissions(Long roleId) {
        LambdaQueryWrapper<RolePermissionDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RolePermissionDO::getRoleId, roleId);
        rolePermissionService.remove(wrapper);
    }

    @Override
    public List<Long> findPermissionIdsByRoleId(Long roleId) {
        LambdaQueryWrapper<RolePermissionDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RolePermissionDO::getRoleId, roleId);
        List<RolePermissionDO> rolePermissionDOList = rolePermissionService.list(wrapper);
        return rolePermissionDOList.stream().map(RolePermissionDO::getPermissionId).toList();
    }
}
