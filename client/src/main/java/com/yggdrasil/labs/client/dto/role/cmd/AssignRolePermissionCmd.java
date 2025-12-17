package com.yggdrasil.labs.client.dto.role.cmd;

import java.util.List;

import jakarta.validation.constraints.NotNull;

import com.alibaba.cola.dto.Command;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分配角色权限命令
 *
 * @author YoungerYang-Y
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AssignRolePermissionCmd extends Command {

    /** 角色ID */
    @NotNull(message = "角色ID不能为空")
    private Long roleId;

    /** 权限ID列表 */
    @NotNull(message = "权限ID列表不能为空")
    private List<Long> permissionIds;
}
