package com.yggdrasil.labs.client.dto.permission.query;

import com.alibaba.cola.dto.Query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分页查询权限
 *
 * @author YoungerYang-Y
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PagePermissionQuery extends Query {

    /** 模块 */
    private String module;

    /** 权限名称（模糊匹配） */
    private String permissionName;

    /** 页码（从1开始） */
    private Integer pageNum = 1;

    /** 每页数量 */
    private Integer pageSize = 10;
}
