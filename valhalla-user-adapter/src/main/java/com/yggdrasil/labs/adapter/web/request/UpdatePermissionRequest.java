package com.yggdrasil.labs.adapter.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * 更新权限请求
 *
 * @author YoungerYang-Y
 */
@Data
public class UpdatePermissionRequest {

    /** 权限ID */
    @NotNull(message = "权限ID不能为空")
    private Long id;

    /** 权限名称 */
    @NotBlank(message = "权限名称不能为空")
    private String permissionName;

    /** 权限描述 */
    private String description;

    /** 扩展信息（JSON） */
    private String metadata;
}
