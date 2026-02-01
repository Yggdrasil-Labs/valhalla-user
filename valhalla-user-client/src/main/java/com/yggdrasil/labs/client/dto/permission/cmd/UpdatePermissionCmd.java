package com.yggdrasil.labs.client.dto.permission.cmd;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.alibaba.cola.dto.Command;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 更新权限命令
 *
 * @author YoungerYang-Y
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UpdatePermissionCmd extends Command {

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
