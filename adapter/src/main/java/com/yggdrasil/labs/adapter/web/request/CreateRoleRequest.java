package com.yggdrasil.labs.adapter.web.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 创建角色请求
 *
 * @author YoungerYang-Y
 */
@Data
public class CreateRoleRequest {

    /** 角色代码 */
    @NotBlank(message = "角色代码不能为空")
    private String roleCode;

    /** 角色名称 */
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    /** 角色描述 */
    private String description;

    /** 是否系统角色（不可删除） */
    private Boolean isSystem;

    /** 扩展信息（JSON） */
    private String metadata;

    /** 权限ID列表 */
    private List<Long> permissionIds;
}
