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

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.yggdrasil.labs.adapter.web.convert.RoleWebConverter;
import com.yggdrasil.labs.adapter.web.request.AssignRolePermissionRequest;
import com.yggdrasil.labs.adapter.web.request.CreateRoleRequest;
import com.yggdrasil.labs.adapter.web.request.PageRoleRequest;
import com.yggdrasil.labs.adapter.web.request.UpdateRoleRequest;
import com.yggdrasil.labs.client.api.RoleClient;
import com.yggdrasil.labs.client.dto.role.co.RoleCO;

/**
 * 角色控制器
 *
 * @author YoungerYang-Y
 */
@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    @Resource private RoleClient roleClient;

    @Resource private RoleWebConverter roleWebConverter;

    /**
     * 创建角色
     *
     * @param request 创建角色请求
     * @return 响应结果
     */
    @PostMapping
    public Response createRole(@Valid @RequestBody CreateRoleRequest request) {
        return roleClient.createRole(roleWebConverter.toCreateRoleCmd(request));
    }

    /**
     * 更新角色
     *
     * @param id 角色ID
     * @param request 更新角色请求
     * @return 响应结果
     */
    @PutMapping("/{id}")
    public Response updateRole(
            @PathVariable Long id, @Valid @RequestBody UpdateRoleRequest request) {
        request.setId(id);
        return roleClient.updateRole(roleWebConverter.toUpdateRoleCmd(request));
    }

    /**
     * 删除角色
     *
     * @param id 角色ID
     * @return 响应结果
     */
    @DeleteMapping("/{id}")
    public Response deleteRole(@PathVariable Long id) {
        return roleClient.deleteRole(roleWebConverter.toDeleteRoleCmd(id));
    }

    /**
     * 获取角色详情
     *
     * @param id 角色ID
     * @return 角色信息
     */
    @GetMapping("/{id}")
    public SingleResponse<RoleCO> getRole(@PathVariable Long id) {
        return roleClient.getRole(roleWebConverter.toGetRoleQuery(id));
    }

    /**
     * 分页查询角色列表
     *
     * @param request 查询请求
     * @return 角色列表
     */
    @GetMapping
    public MultiResponse<RoleCO> pageRole(@Valid PageRoleRequest request) {
        return roleClient.pageRole(roleWebConverter.toPageRoleQuery(request));
    }

    /**
     * 分配角色权限
     *
     * @param id 角色ID
     * @param request 分配权限请求
     * @return 响应结果
     */
    @PostMapping("/{id}/permissions")
    public Response assignRolePermission(
            @PathVariable Long id, @Valid @RequestBody AssignRolePermissionRequest request) {
        request.setRoleId(id);
        return roleClient.assignRolePermission(roleWebConverter.toAssignRolePermissionCmd(request));
    }
}
