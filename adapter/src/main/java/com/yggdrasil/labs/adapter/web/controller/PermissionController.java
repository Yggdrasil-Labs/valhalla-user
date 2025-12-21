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
import com.yggdrasil.labs.adapter.web.convert.PermissionWebConverter;
import com.yggdrasil.labs.adapter.web.request.AssignPermissionApiRequest;
import com.yggdrasil.labs.adapter.web.request.CreatePermissionRequest;
import com.yggdrasil.labs.adapter.web.request.PagePermissionRequest;
import com.yggdrasil.labs.adapter.web.request.UpdatePermissionRequest;
import com.yggdrasil.labs.client.api.PermissionClient;
import com.yggdrasil.labs.client.dto.permission.co.PermissionCO;

/**
 * 权限控制器
 *
 * @author YoungerYang-Y
 */
@RestController
@RequestMapping("/api/v1/permissions")
public class PermissionController {

    @Resource private PermissionClient permissionClient;

    @Resource private PermissionWebConverter permissionWebConverter;

    /**
     * 创建权限
     *
     * @param request 创建权限请求
     * @return 响应结果
     */
    @PostMapping
    public Response createPermission(@Valid @RequestBody CreatePermissionRequest request) {
        return permissionClient.createPermission(
                permissionWebConverter.toCreatePermissionCmd(request));
    }

    /**
     * 更新权限
     *
     * @param id 权限ID
     * @param request 更新权限请求
     * @return 响应结果
     */
    @PutMapping("/{id}")
    public Response updatePermission(
            @PathVariable Long id, @Valid @RequestBody UpdatePermissionRequest request) {
        request.setId(id);
        return permissionClient.updatePermission(
                permissionWebConverter.toUpdatePermissionCmd(request));
    }

    /**
     * 删除权限
     *
     * @param id 权限ID
     * @return 响应结果
     */
    @DeleteMapping("/{id}")
    public Response deletePermission(@PathVariable Long id) {
        return permissionClient.deletePermission(permissionWebConverter.toDeletePermissionCmd(id));
    }

    /**
     * 获取权限详情
     *
     * @param id 权限ID
     * @return 权限信息
     */
    @GetMapping("/{id}")
    public SingleResponse<PermissionCO> getPermission(@PathVariable Long id) {
        return permissionClient.getPermission(permissionWebConverter.toGetPermissionQuery(id));
    }

    /**
     * 分页查询权限列表
     *
     * @param request 查询请求
     * @return 权限列表
     */
    @GetMapping
    public MultiResponse<PermissionCO> pagePermission(@Valid PagePermissionRequest request) {
        return permissionClient.pagePermission(
                permissionWebConverter.toPagePermissionQuery(request));
    }

    /**
     * 分配权限API
     *
     * @param id 权限ID（从URL路径获取）
     * @param request 分配API请求（只包含API ID列表）
     * @return 响应结果
     */
    @PostMapping("/{id}/apis")
    public Response assignPermissionApi(
            @PathVariable Long id, @Valid @RequestBody AssignPermissionApiRequest request) {
        return permissionClient.assignPermissionApi(
                permissionWebConverter.toAssignPermissionApiCmd(id, request));
    }
}
