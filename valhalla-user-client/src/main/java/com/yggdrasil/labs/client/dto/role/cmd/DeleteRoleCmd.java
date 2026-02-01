package com.yggdrasil.labs.client.dto.role.cmd;

import jakarta.validation.constraints.NotNull;

import com.alibaba.cola.dto.Command;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 删除角色命令
 *
 * @author YoungerYang-Y
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DeleteRoleCmd extends Command {

    /** 角色ID */
    @NotNull(message = "角色ID不能为空")
    private Long id;
}
