package com.yggdrasil.labs.client.dto.role.query;

import jakarta.validation.constraints.NotNull;

import com.alibaba.cola.dto.Query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 获取角色查询
 *
 * @author YoungerYang-Y
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GetRoleQuery extends Query {

    /** 角色ID */
    @NotNull(message = "角色ID不能为空")
    private Long id;
}
