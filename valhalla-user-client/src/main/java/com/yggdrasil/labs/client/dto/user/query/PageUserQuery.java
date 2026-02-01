package com.yggdrasil.labs.client.dto.user.query;

import com.alibaba.cola.dto.Query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分页查询用户
 *
 * @author YoungerYang-Y
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PageUserQuery extends Query {

    /** 用户名（模糊匹配） */
    private String username;

    /** 状态：0-禁用，1-启用 */
    private Integer status;

    /** 页码（从1开始） */
    private Integer pageNum = 1;

    /** 每页数量 */
    private Integer pageSize = 10;
}
