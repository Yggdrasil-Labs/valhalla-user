package com.yggdrasil.labs.client.dto.role.cmd;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.alibaba.cola.dto.Command;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 更新角色命令
 *
 * @author YoungerYang-Y
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UpdateRoleCmd extends Command {

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
