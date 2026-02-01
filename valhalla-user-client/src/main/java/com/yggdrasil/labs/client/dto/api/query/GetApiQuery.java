package com.yggdrasil.labs.client.dto.api.query;

import jakarta.validation.constraints.NotNull;

import com.alibaba.cola.dto.Query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 获取API查询
 *
 * @author YoungerYang-Y
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GetApiQuery extends Query {

    /** 接口ID */
    @NotNull(message = "接口ID不能为空")
    private Long id;
}
