package com.yggdrasil.labs.adapter.web.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * 分配用户角色请求
 *
 * @author YoungerYang-Y
 */
@Data
public class AssignUserRoleRequest {

    /** 用户ID */
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    /** 角色ID列表 */
    @NotNull(message = "角色ID列表不能为空")
    private List<Long> roleIds;
}
