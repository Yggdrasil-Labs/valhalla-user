package com.yggdrasil.labs.client.dto.user.cmd;

import java.util.List;

import jakarta.validation.constraints.NotNull;

import com.alibaba.cola.dto.Command;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分配用户角色命令
 *
 * @author YoungerYang-Y
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AssignUserRoleCmd extends Command {

    /** 用户ID */
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    /** 角色ID列表 */
    @NotNull(message = "角色ID列表不能为空")
    private List<Long> roleIds;
}
