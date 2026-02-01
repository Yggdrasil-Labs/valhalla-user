package com.yggdrasil.labs.adapter.web.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * 分配角色权限请求
 *
 * <p>注意：角色ID从URL路径参数中获取，不需要在请求体中传递
 *
 * @author YoungerYang-Y
 */
@Data
public class AssignRolePermissionRequest {

    /** 权限ID列表 */
    @NotNull(message = "权限ID列表不能为空")
    private List<Long> permissionIds;
}
