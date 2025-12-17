package com.yggdrasil.labs.adapter.web.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * 分配权限API请求
 *
 * @author YoungerYang-Y
 */
@Data
public class AssignPermissionApiRequest {

    /** 权限ID */
    @NotNull(message = "权限ID不能为空")
    private Long permissionId;

    /** API ID列表 */
    @NotNull(message = "API ID列表不能为空")
    private List<Long> apiIds;
}
