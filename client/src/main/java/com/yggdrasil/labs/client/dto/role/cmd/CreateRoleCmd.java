package com.yggdrasil.labs.client.dto.role.cmd;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

import com.alibaba.cola.dto.Command;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 创建角色命令
 *
 * @author YoungerYang-Y
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CreateRoleCmd extends Command {

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
