package com.yggdrasil.labs.client.dto.user.query;

import jakarta.validation.constraints.NotNull;

import com.alibaba.cola.dto.Query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 获取用户查询
 *
 * @author YoungerYang-Y
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GetUserQuery extends Query {

    /** 用户ID */
    @NotNull(message = "用户ID不能为空")
    private Long id;
}
