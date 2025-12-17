package com.yggdrasil.labs.client.dto.permission.cmd;

import java.util.List;

import jakarta.validation.constraints.NotNull;

import com.alibaba.cola.dto.Command;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分配权限API命令
 *
 * @author YoungerYang-Y
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AssignPermissionApiCmd extends Command {

    /** 权限ID */
    @NotNull(message = "权限ID不能为空")
    private Long permissionId;

    /** API ID列表 */
    @NotNull(message = "API ID列表不能为空")
    private List<Long> apiIds;
}
