package com.yggdrasil.labs.infrastructure.persistence.impl;

import java.util.List;

import jakarta.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yggdrasil.labs.domain.user.model.User;
import com.yggdrasil.labs.domain.user.repository.UserRepository;
import com.yggdrasil.labs.infrastructure.persistence.converter.UserConverter;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.UserDO;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.UserRoleDO;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.service.UserRoleService;
import com.yggdrasil.labs.infrastructure.persistence.dataobject.service.UserService;

/**
 * 用户仓储实现
 *
 * @author YoungerYang-Y
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    @Resource private UserService userService;

    @Resource private UserRoleService userRoleService;

    @Resource private UserConverter userConverter;

    @Override
    public User findById(Long id) {
        UserDO userDO = userService.getById(id);
        if (userDO == null) {
            return null;
        }
        User user = userConverter.toEntity(userDO);
        // 加载角色ID列表
        List<Long> roleIds = findRoleIdsByUserId(id);
        user.setRoleIds(roleIds);
        return user;
    }

    @Override
    public User findByUsername(String username) {
        LambdaQueryWrapper<UserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserDO::getUsername, username);
        UserDO userDO = userService.getOne(wrapper);
        if (userDO == null) {
            return null;
        }
        User user = userConverter.toEntity(userDO);
        List<Long> roleIds = findRoleIdsByUserId(userDO.getId());
        user.setRoleIds(roleIds);
        return user;
    }

    @Override
    public User findByEmail(String email) {
        LambdaQueryWrapper<UserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserDO::getEmail, email);
        UserDO userDO = userService.getOne(wrapper);
        if (userDO == null) {
            return null;
        }
        User user = userConverter.toEntity(userDO);
        List<Long> roleIds = findRoleIdsByUserId(userDO.getId());
        user.setRoleIds(roleIds);
        return user;
    }

    @Override
    public User findByPhone(String phone) {
        LambdaQueryWrapper<UserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserDO::getPhone, phone);
        UserDO userDO = userService.getOne(wrapper);
        if (userDO == null) {
            return null;
        }
        User user = userConverter.toEntity(userDO);
        List<Long> roleIds = findRoleIdsByUserId(userDO.getId());
        user.setRoleIds(roleIds);
        return user;
    }

    @Override
    public void save(User user) {
        UserDO userDO = userConverter.toDO(user);
        userService.save(userDO);
        user.setId(userDO.getId());
        // 保存用户角色关联
        if (user.getRoleIds() != null && !user.getRoleIds().isEmpty()) {
            saveUserRoles(user.getId(), user.getRoleIds());
        }
    }

    @Override
    public void update(User user) {
        UserDO userDO = userConverter.toDO(user);
        userService.updateById(userDO);
        // 更新用户角色关联
        deleteUserRoles(user.getId());
        if (user.getRoleIds() != null && !user.getRoleIds().isEmpty()) {
            saveUserRoles(user.getId(), user.getRoleIds());
        }
    }

    @Override
    public void delete(Long id) {
        userService.removeById(id);
        // 删除用户角色关联
        deleteUserRoles(id);
    }

    @Override
    public List<User> findPage(String username, Integer status, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<UserDO> wrapper = new LambdaQueryWrapper<>();
        if (username != null && !username.trim().isEmpty()) {
            wrapper.like(UserDO::getUsername, username);
        }
        if (status != null) {
            wrapper.eq(UserDO::getStatus, status);
        }
        Page<UserDO> page = new Page<>(pageNum, pageSize);
        IPage<UserDO> pageResult = userService.page(page, wrapper);
        List<UserDO> userDOList = pageResult.getRecords();
        return userDOList.stream()
                .map(
                        userDO -> {
                            User user = userConverter.toEntity(userDO);
                            List<Long> roleIds = findRoleIdsByUserId(userDO.getId());
                            user.setRoleIds(roleIds);
                            return user;
                        })
                .toList();
    }

    @Override
    public long count(String username, Integer status) {
        LambdaQueryWrapper<UserDO> wrapper = new LambdaQueryWrapper<>();
        if (username != null && !username.trim().isEmpty()) {
            wrapper.like(UserDO::getUsername, username);
        }
        if (status != null) {
            wrapper.eq(UserDO::getStatus, status);
        }
        return userService.count(wrapper);
    }

    @Override
    public boolean existsByUsername(String username) {
        LambdaQueryWrapper<UserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserDO::getUsername, username);
        return userService.count(wrapper) > 0;
    }

    @Override
    public boolean existsByEmail(String email) {
        LambdaQueryWrapper<UserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserDO::getEmail, email);
        return userService.count(wrapper) > 0;
    }

    @Override
    public boolean existsByPhone(String phone) {
        LambdaQueryWrapper<UserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserDO::getPhone, phone);
        return userService.count(wrapper) > 0;
    }

    @Override
    public void saveUserRoles(Long userId, List<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return;
        }
        for (Long roleId : roleIds) {
            UserRoleDO userRoleDO = new UserRoleDO();
            userRoleDO.setUserId(userId);
            userRoleDO.setRoleId(roleId);
            userRoleService.save(userRoleDO);
        }
    }

    @Override
    public void deleteUserRoles(Long userId) {
        LambdaQueryWrapper<UserRoleDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRoleDO::getUserId, userId);
        userRoleService.remove(wrapper);
    }

    @Override
    public List<Long> findRoleIdsByUserId(Long userId) {
        LambdaQueryWrapper<UserRoleDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRoleDO::getUserId, userId);
        List<UserRoleDO> userRoleDOList = userRoleService.list(wrapper);
        return userRoleDOList.stream().map(UserRoleDO::getRoleId).toList();
    }
}
