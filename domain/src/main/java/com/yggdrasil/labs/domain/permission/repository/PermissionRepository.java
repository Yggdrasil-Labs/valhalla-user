package com.yggdrasil.labs.domain.permission.repository;

import java.util.List;

import com.yggdrasil.labs.domain.permission.model.Permission;

/**
 * 权限仓储接口
 *
 * @author YoungerYang-Y
 */
public interface PermissionRepository {

    /**
     * 根据ID查询权限
     *
     * @param id 权限ID
     * @return 权限实体，不存在返回null
     */
    Permission findById(Long id);

    /**
     * 根据权限代码查询权限
     *
     * @param permissionCode 权限代码
     * @return 权限实体，不存在返回null
     */
    Permission findByPermissionCode(String permissionCode);

    /**
     * 根据模块、资源、操作查询权限
     *
     * @param module 模块
     * @param resource 资源
     * @param action 操作
     * @return 权限实体，不存在返回null
     */
    Permission findByModuleResourceAction(String module, String resource, String action);

    /**
     * 保存权限（新增）
     *
     * @param permission 权限实体
     */
    void save(Permission permission);

    /**
     * 更新权限
     *
     * @param permission 权限实体
     */
    void update(Permission permission);

    /**
     * 删除权限（软删除）
     *
     * @param id 权限ID
     */
    void delete(Long id);

    /**
     * 根据条件分页查询权限列表
     *
     * @param module 模块（可选）
     * @param permissionName 权限名称（可选，模糊匹配）
     * @param pageNum 页码（从1开始）
     * @param pageSize 每页数量
     * @return 权限列表
     */
    List<Permission> findPage(
            String module, String permissionName, Integer pageNum, Integer pageSize);

    /**
     * 统计符合条件的权限数量
     *
     * @param module 模块（可选）
     * @param permissionName 权限名称（可选，模糊匹配）
     * @return 权限数量
     */
    long count(String module, String permissionName);

    /**
     * 检查权限代码是否存在
     *
     * @param permissionCode 权限代码
     * @return true-存在，false-不存在
     */
    boolean existsByPermissionCode(String permissionCode);

    /**
     * 检查模块、资源、操作组合是否存在
     *
     * @param module 模块
     * @param resource 资源
     * @param action 操作
     * @return true-存在，false-不存在
     */
    boolean existsByModuleResourceAction(String module, String resource, String action);

    /**
     * 保存权限API关联
     *
     * @param permissionId 权限ID
     * @param apiIds API ID列表
     */
    void savePermissionApis(Long permissionId, List<Long> apiIds);

    /**
     * 删除权限API关联
     *
     * @param permissionId 权限ID
     */
    void deletePermissionApis(Long permissionId);

    /**
     * 查询权限的API ID列表
     *
     * @param permissionId 权限ID
     * @return API ID列表
     */
    List<Long> findApiIdsByPermissionId(Long permissionId);
}
