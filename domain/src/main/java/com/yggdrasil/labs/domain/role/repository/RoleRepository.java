package com.yggdrasil.labs.domain.role.repository;

import java.util.List;

import com.yggdrasil.labs.domain.role.model.Role;

/**
 * 角色仓储接口
 *
 * @author YoungerYang-Y
 */
public interface RoleRepository {

    /**
     * 根据ID查询角色
     *
     * @param id 角色ID
     * @return 角色实体，不存在返回null
     */
    Role findById(Long id);

    /**
     * 根据角色代码查询角色
     *
     * @param roleCode 角色代码
     * @return 角色实体，不存在返回null
     */
    Role findByRoleCode(String roleCode);

    /**
     * 保存角色（新增）
     *
     * @param role 角色实体
     */
    void save(Role role);

    /**
     * 更新角色
     *
     * @param role 角色实体
     */
    void update(Role role);

    /**
     * 删除角色（软删除）
     *
     * @param id 角色ID
     */
    void delete(Long id);

    /**
     * 根据条件分页查询角色列表
     *
     * @param roleName 角色名称（可选，模糊匹配）
     * @param roleCode 角色代码（可选，模糊匹配）
     * @param pageNum 页码（从1开始）
     * @param pageSize 每页数量
     * @return 角色列表
     */
    List<Role> findPage(String roleName, String roleCode, Integer pageNum, Integer pageSize);

    /**
     * 统计符合条件的角色数量
     *
     * @param roleName 角色名称（可选，模糊匹配）
     * @param roleCode 角色代码（可选，模糊匹配）
     * @return 角色数量
     */
    long count(String roleName, String roleCode);

    /**
     * 检查角色代码是否存在
     *
     * @param roleCode 角色代码
     * @return true-存在，false-不存在
     */
    boolean existsByRoleCode(String roleCode);

    /**
     * 保存角色权限关联
     *
     * @param roleId 角色ID
     * @param permissionIds 权限ID列表
     */
    void saveRolePermissions(Long roleId, List<Long> permissionIds);

    /**
     * 删除角色权限关联
     *
     * @param roleId 角色ID
     */
    void deleteRolePermissions(Long roleId);

    /**
     * 查询角色的权限ID列表
     *
     * @param roleId 角色ID
     * @return 权限ID列表
     */
    List<Long> findPermissionIdsByRoleId(Long roleId);
}
