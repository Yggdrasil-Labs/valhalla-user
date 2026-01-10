package com.yggdrasil.labs.app.permission.dto.cmd;

import jakarta.validation.constraints.NotNull;

import com.alibaba.cola.dto.Command;

import lombok.Data;
import lombok.EqualsAndHashCode;

/** 删除权限命令 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DeletePermissionCmd extends Command {

    /** 权限ID */
    @NotNull(message = "权限ID不能为空")
    private Long id;
}
