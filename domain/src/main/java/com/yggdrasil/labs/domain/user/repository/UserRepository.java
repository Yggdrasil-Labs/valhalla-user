package com.yggdrasil.labs.domain.user.repository;

import java.util.List;

import com.yggdrasil.labs.domain.user.model.User;

/**
 * 用户仓储接口
 *
 * @author YoungerYang-Y
 */
public interface UserRepository {

    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return 用户实体，不存在返回null
     */
    User findById(Long id);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户实体，不存在返回null
     */
    User findByUsername(String username);

    /**
     * 根据邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户实体，不存在返回null
     */
    User findByEmail(String email);

    /**
     * 根据手机号查询用户
     *
     * @param phone 手机号
     * @return 用户实体，不存在返回null
     */
    User findByPhone(String phone);

    /**
     * 保存用户（新增）
     *
     * @param user 用户实体
     */
    void save(User user);

    /**
     * 更新用户
     *
     * @param user 用户实体
     */
    void update(User user);

    /**
     * 删除用户（软删除）
     *
     * @param id 用户ID
     */
    void delete(Long id);

    /**
     * 根据条件分页查询用户列表
     *
     * @param username 用户名（可选，模糊匹配）
     * @param status 状态（可选）
     * @param pageNum 页码（从1开始）
     * @param pageSize 每页数量
     * @return 用户列表
     */
    List<User> findPage(String username, Integer status, Integer pageNum, Integer pageSize);

    /**
     * 统计符合条件的用户数量
     *
     * @param username 用户名（可选，模糊匹配）
     * @param status 状态（可选）
     * @return 用户数量
     */
    long count(String username, Integer status);

    /**
     * 检查用户名是否存在
     *
     * @param username 用户名
     * @return true-存在，false-不存在
     */
    boolean existsByUsername(String username);

    /**
     * 检查邮箱是否存在
     *
     * @param email 邮箱
     * @return true-存在，false-不存在
     */
    boolean existsByEmail(String email);

    /**
     * 检查手机号是否存在
     *
     * @param phone 手机号
     * @return true-存在，false-不存在
     */
    boolean existsByPhone(String phone);

    /**
     * 保存用户角色关联
     *
     * @param userId 用户ID
     * @param roleIds 角色ID列表
     */
    void saveUserRoles(Long userId, List<Long> roleIds);

    /**
     * 删除用户角色关联
     *
     * @param userId 用户ID
     */
    void deleteUserRoles(Long userId);

    /**
     * 查询用户的角色ID列表
     *
     * @param userId 用户ID
     * @return 角色ID列表
     */
    List<Long> findRoleIdsByUserId(Long userId);
}
