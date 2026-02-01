package com.yggdrasil.labs.client.dto.permission.query;

import jakarta.validation.constraints.NotNull;

import com.alibaba.cola.dto.Query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 获取权限查询
 *
 * @author YoungerYang-Y
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GetPermissionQuery extends Query {

    /** 权限ID */
    @NotNull(message = "权限ID不能为空")
    private Long id;
}
