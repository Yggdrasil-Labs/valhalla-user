package com.yggdrasil.labs.adapter.web.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * 分配角色权限请求
 *
 * @author YoungerYang-Y
 */
@Data
public class AssignRolePermissionRequest {

    /** 角色ID */
    @NotNull(message = "角色ID不能为空")
    private Long roleId;

    /** 权限ID列表 */
    @NotNull(message = "权限ID列表不能为空")
    private List<Long> permissionIds;
}
