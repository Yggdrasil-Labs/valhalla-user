package com.yggdrasil.labs.adapter.web.controller;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.cola.dto.PageResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.adapter.web.convert.UserWebConverter;
import com.yggdrasil.labs.adapter.web.request.AssignUserRoleRequest;
import com.yggdrasil.labs.adapter.web.request.CreateUserRequest;
import com.yggdrasil.labs.adapter.web.request.PageUserRequest;
import com.yggdrasil.labs.adapter.web.request.UpdateUserRequest;
import com.yggdrasil.labs.app.service.UserApplicationService;
import com.yggdrasil.labs.app.user.dto.co.UserCO;

/**
 * 用户控制器
 *
 * @author YoungerYang-Y
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Resource private UserApplicationService userApplicationService;

    @Resource private UserWebConverter userWebConverter;

    /**
     * 创建用户
     *
     * @param request 创建用户请求
     * @return 响应结果
     */
    @PostMapping
    public Response createUser(@Valid @RequestBody CreateUserRequest request) {
        return userApplicationService.createUser(userWebConverter.toCreateUserCmd(request));
    }

    /**
     * 更新用户
     *
     * @param id 用户ID
     * @param request 更新用户请求
     * @return 响应结果
     */
    @PutMapping("/{id}")
    public Response updateUser(
            @PathVariable Long id, @Valid @RequestBody UpdateUserRequest request) {
        request.setId(id);
        return userApplicationService.updateUser(userWebConverter.toUpdateUserCmd(request));
    }

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 响应结果
     */
    @DeleteMapping("/{id}")
    public Response deleteUser(@PathVariable Long id) {
        return userApplicationService.deleteUser(userWebConverter.toDeleteUserCmd(id));
    }

    /**
     * 获取用户详情
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @GetMapping("/{id}")
    public SingleResponse<UserCO> getUser(@PathVariable Long id) {
        return userApplicationService.getUser(userWebConverter.toGetUserQuery(id));
    }

    /**
     * 分页查询用户列表
     *
     * @param request 查询请求
     * @return 用户列表
     */
    @GetMapping
    public PageResponse<UserCO> pageUser(@Valid PageUserRequest request) {
        return userApplicationService.pageUser(userWebConverter.toPageUserQuery(request));
    }

    /**
     * 分配用户角色
     *
     * @param id 用户ID（从URL路径获取）
     * @param request 分配角色请求（只包含角色ID列表）
     * @return 响应结果
     */
    @PostMapping("/{id}/roles")
    public Response assignUserRole(
            @PathVariable Long id, @Valid @RequestBody AssignUserRoleRequest request) {
        return userApplicationService.assignUserRole(
                userWebConverter.toAssignUserRoleCmd(id, request));
    }
}
