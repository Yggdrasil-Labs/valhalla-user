package com.yggdrasil.labs.adapter.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * 更新角色请求
 *
 * @author YoungerYang-Y
 */
@Data
public class UpdateRoleRequest {

    /** 角色ID */
    @NotNull(message = "角色ID不能为空")
    private Long id;

    /** 角色名称 */
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    /** 角色描述 */
    private String description;

    /** 扩展信息（JSON） */
    private String metadata;
}
