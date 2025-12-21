package com.yggdrasil.labs.adapter.web.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * 分配用户角色请求
 *
 * <p>注意：用户ID从URL路径参数中获取，不需要在请求体中传递
 *
 * @author YoungerYang-Y
 */
@Data
public class AssignUserRoleRequest {

    /** 角色ID列表 */
    @NotNull(message = "角色ID列表不能为空")
    private List<Long> roleIds;
}
